package com.michael.bankingLoans.service;

import com.michael.bankingLoans.dto.LoanDto;

public interface ILoanService {
    void createLoan(String mobileNumber);

    LoanDto fetchLoan(String mobileNumber);

    boolean updateLoan(LoanDto loansDto);

    boolean deleteLoan(String mobileNumber);
}
