package ru.clevertec.check.domain.service;

import ru.clevertec.check.domain.model.entity.RealDiscountCard;
import ru.clevertec.check.domain.model.valueobject.CardNumber;

import java.util.List;
import java.util.Optional;

public class DiscountCardService {
    public static Optional<RealDiscountCard> findByCardNumber(List<RealDiscountCard> discountCards, CardNumber cardNumber) {
        return discountCards.stream()
                .filter(discountCard -> discountCard.getCardNumber().equals(cardNumber))
                .findFirst();

    }





}
