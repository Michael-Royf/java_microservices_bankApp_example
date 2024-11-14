package com.michael.bankingCards.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Cards extends BaseEntity{
    @Id
    @SequenceGenerator(name = "card_id_sequence", sequenceName = "card_id_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "card_id_sequence")
    @Column(name = "card_id")
    private Long cardId;

    private String mobileNumber;

    private String cardNumber;

    private String cardType;

    private int totalLimit;

    private int amountUsed;

    private int availableAmount;
}
