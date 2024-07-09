package ru.clevertec.check.domain.model.entity;

import ru.clevertec.check.domain.model.valueobject.CardId;
import ru.clevertec.check.domain.model.valueobject.CardNumber;

import java.math.BigDecimal;

public interface DiscountCard {

    CardId getId();

    CardNumber getCardNumber();

    boolean isValid();

    BigDecimal getDiscountAmount();

    void addCardNumber(CardNumber cardNumber);

    void addDiscountAmount(BigDecimal discountAmount);

}
