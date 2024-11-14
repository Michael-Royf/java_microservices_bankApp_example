package com.michael.bankingAccounts.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class Accounts extends BaseEntity {
    @Column(name = "customer_id")
    private Long customerId;

    @SequenceGenerator(name = "account_id_sequence", sequenceName = "account_id_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_id_sequence")
    @Id
    private Long accountNumber;

    @Column(name = "account_type")
    private String accountType;

    @Column(name = "branch_address")
    private String branchAddress;
}
