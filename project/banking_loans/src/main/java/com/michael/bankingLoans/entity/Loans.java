package com.michael.bankingLoans.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Loans extends BaseEntity {
    @Id
    @SequenceGenerator(name = "loan_id_sequence", sequenceName = "loan_id_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "loan_id_sequence")
    @Column(name = "loan_id")
    private Long loanId;

    private String mobileNumber;

    private String loanNumber;

    private String loanType;

    private int totalLoan;

    private int amountPaid;

    private int outstandingAmount;
}
