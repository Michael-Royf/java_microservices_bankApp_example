package com.michael.bankingAccounts.service;

import com.michael.bankingAccounts.dto.AccountsDto;
import com.michael.bankingAccounts.dto.CustomerDto;

public interface IAccountService {

    void createAccount(CustomerDto customerDto);

    CustomerDto fetchAccount(String email);

    boolean updateAccount(CustomerDto customerDto);

    boolean deleteAccount(String mobileNumber);
}
