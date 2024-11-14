package com.michael.bankingAccounts.service.impl;

import com.michael.bankingAccounts.constants.AccountsConstants;
import com.michael.bankingAccounts.dto.AccountsDto;
import com.michael.bankingAccounts.dto.CustomerDto;
import com.michael.bankingAccounts.entity.Accounts;
import com.michael.bankingAccounts.entity.Customer;
import com.michael.bankingAccounts.exceptions.CustomerAlreadyExistsException;
import com.michael.bankingAccounts.exceptions.ResourceNotFoundException;
import com.michael.bankingAccounts.mapper.AccountsMapper;
import com.michael.bankingAccounts.mapper.CustomerMapper;
import com.michael.bankingAccounts.repository.AccountRepository;
import com.michael.bankingAccounts.repository.CustomerRepository;
import com.michael.bankingAccounts.service.IAccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements IAccountService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final ModelMapper mapper;


    @Override
    public void createAccount(CustomerDto customerDto) {

        if (customerRepository.existsByEmail(customerDto.getEmail()) || customerRepository.existsByMobileNumber(customerDto.getMobileNumber())) {
            throw new CustomerAlreadyExistsException("Customer with the provided email or mobile number already exists");
        }

        Customer customer = Customer.builder()
                .name(customerDto.getName())
                .email(customerDto.getEmail())
                .mobileNumber(customerDto.getMobileNumber())
                .build();
        customer = customerRepository.save(customer);
        accountRepository.save(createNewAccount(customer));

    }

    @Override
    public CustomerDto fetchAccount(String email) {
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(()-> new ResourceNotFoundException("Customer", "email", email));
        Accounts accounts = accountRepository.findByCustomerId(customer.getCustomerId())
                .orElseThrow(()-> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString()));
        CustomerDto customerDto = mapper.map(customer, CustomerDto.class);
        AccountsDto accountsDto = mapper.map(accounts, AccountsDto.class);
        customerDto.setAccountsDto(accountsDto);
        return customerDto;
    }

    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        boolean isUpdated = false;
        AccountsDto accountsDto = customerDto.getAccountsDto();
        if(accountsDto !=null ){
            Accounts accounts = accountRepository.findById(accountsDto.getAccountNumber()).orElseThrow(
                    () -> new ResourceNotFoundException("Account", "AccountNumber", accountsDto.getAccountNumber().toString())
            );
            AccountsMapper.mapToAccounts(accountsDto, accounts);
            accounts = accountRepository.save(accounts);

            Long customerId = accounts.getCustomerId();
            Customer customer = customerRepository.findById(customerId).orElseThrow(
                    () -> new ResourceNotFoundException("Customer", "CustomerID", customerId.toString())
            );
            CustomerMapper.mapToCustomer(customerDto,customer);
            customerRepository.save(customer);
            isUpdated = true;
        }
        return  isUpdated;
    }

    @Override
    public boolean deleteAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        accountRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());
        return true;
    }

    private Accounts createNewAccount(Customer customer) {
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);
        return Accounts.builder()
                .customerId(customer.getCustomerId())
                .accountNumber(randomAccNumber)
                .accountType(AccountsConstants.SAVINGS)
                .branchAddress(AccountsConstants.ADDRESS)
                .build();
    }


}
