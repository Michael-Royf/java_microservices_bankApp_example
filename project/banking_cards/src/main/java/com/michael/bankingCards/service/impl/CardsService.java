package com.michael.bankingCards.service.impl;

import com.michael.bankingCards.constants.CardsConstants;
import com.michael.bankingCards.dto.CardsDto;
import com.michael.bankingCards.entity.Cards;
import com.michael.bankingCards.exceptions.CardAlreadyExistsException;
import com.michael.bankingCards.exceptions.ResourceNotFoundException;
import com.michael.bankingCards.repository.CardsRepository;
import com.michael.bankingCards.service.ICardsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;


@Service
@RequiredArgsConstructor
@Slf4j
public class CardsService implements ICardsService {

    private final CardsRepository cardsRepository;
    private final ModelMapper mapper;


    @Override
    public void createCard(String mobileNumber) {
        Optional<Cards> optionalCards = cardsRepository.findByMobileNumber(mobileNumber);
        if (optionalCards.isPresent()) {
            throw new CardAlreadyExistsException("Card already registered with given mobileNumber " + mobileNumber);
        }
        cardsRepository.save(createNewCard(mobileNumber));
    }

    @Override
    public CardsDto fetchCard(String mobileNumber) {
        Cards cards = findCardsEntity(mobileNumber);
        return mapper.map(cards, CardsDto.class);
    }


    @Override
    public boolean updateCard(CardsDto cardsDto) {
        Cards cards = cardsRepository.findByCardNumber(cardsDto.getCardNumber()).orElseThrow(
                () -> new ResourceNotFoundException("Card", "CardNumber", cardsDto.getCardNumber()));
        mapToCards(cardsDto, cards);
        cardsRepository.save(cards);
        return true;
    }

    @Override
    public boolean deleteCard(String mobileNumber) {
        Cards cards = findCardsEntity(mobileNumber);
        cardsRepository.delete(cards);
        return true;
    }

    private Cards createNewCard(String mobileNumber) {
        long randomCardNumber = 100000000000L + new Random().nextInt(900000000);
        return Cards.builder()
                .cardNumber(Long.toString(randomCardNumber))
                .mobileNumber(mobileNumber)
                .cardType(CardsConstants.CREDIT_CARD)
                .totalLimit(CardsConstants.NEW_CARD_LIMIT)
                .amountUsed(0)
                .availableAmount(CardsConstants.NEW_CARD_LIMIT)
                .build();
    }

    private Cards findCardsEntity(String mobileNumber) {
        return cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Card", "mobileNumber", mobileNumber)
        );
    }

    private Cards mapToCards(CardsDto cardsDto, Cards cards) {
        cards.setCardNumber(cardsDto.getCardNumber());
        cards.setCardType(cardsDto.getCardType());
        cards.setMobileNumber(cardsDto.getMobileNumber());
        cards.setTotalLimit(cardsDto.getTotalLimit());
        cards.setAvailableAmount(cardsDto.getAvailableAmount());
        cards.setAmountUsed(cardsDto.getAmountUsed());
        return cards;
    }
}
