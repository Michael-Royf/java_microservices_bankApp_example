package com.michael.bankingLoans.service.impl;

import com.michael.bankingLoans.constans.LoansConstants;
import com.michael.bankingLoans.dto.LoanDto;
import com.michael.bankingLoans.entity.Loans;
import com.michael.bankingLoans.exceptions.LoanAlreadyExistsException;
import com.michael.bankingLoans.exceptions.ResourceNotFoundException;
import com.michael.bankingLoans.repository.LoanRepository;
import com.michael.bankingLoans.service.ILoanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoanService implements ILoanService {

    private final LoanRepository loanRepository;
    private final ModelMapper mapper;


    @Override
    public void createLoan(String mobileNumber) {
        Optional<Loans> optionalLoans = loanRepository.findByMobileNumber(mobileNumber);
        if (optionalLoans.isPresent()) {
            throw new LoanAlreadyExistsException("Loan already registered with given mobileNumber " + mobileNumber);
        }
        loanRepository.save(createNewLoan(mobileNumber));
    }

    private Loans createNewLoan(String mobileNumber) {
        long randomLoanNumber = 100000000000L + new Random().nextInt(900000000);
        return Loans.builder()
                .loanNumber(Long.toString(randomLoanNumber))
                .mobileNumber(mobileNumber)
                .loanType(LoansConstants.HOME_LOAN)
                .totalLoan(LoansConstants.NEW_LOAN_LIMIT)
                .amountPaid(0)
                .outstandingAmount(LoansConstants.NEW_LOAN_LIMIT)
                .build();

    }

    @Override
    public LoanDto fetchLoan(String mobileNumber) {
        Loans loans = findLoansEntity(mobileNumber);
        return mapper.map(loans, LoanDto.class);
    }


    @Override
    public boolean updateLoan(LoanDto loansDto) {
        Loans loans = loanRepository.findByLoanNumber(loansDto.getLoanNumber()).orElseThrow(
                () -> new ResourceNotFoundException("Loan", "LoanNumber", loansDto.getLoanNumber()));
        mapToLoans(loansDto, loans);
        loanRepository.save(loans);
        return true;
    }



    @Override
    public boolean deleteLoan(String mobileNumber) {
        Loans loans = findLoansEntity(mobileNumber);
        loanRepository.delete(loans);
        return true;
    }

    private Loans findLoansEntity(String mobileNumber) {
        return loanRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Loan", "mobileNumber", mobileNumber));
    }

    private void mapToLoans(LoanDto loansDto, Loans loans) {
        loans.setLoanNumber(loansDto.getLoanNumber());
        loans.setLoanType(loansDto.getLoanType());
        loans.setMobileNumber(loansDto.getMobileNumber());
        loans.setTotalLoan(loansDto.getTotalLoan());
        loans.setAmountPaid(loansDto.getAmountPaid());
        loans.setOutstandingAmount(loansDto.getOutstandingAmount());
    }
}
