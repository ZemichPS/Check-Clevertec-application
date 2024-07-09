package ru.clevertec.check.domain.model.entity;

import ru.clevertec.check.domain.model.valueobject.CardId;
import ru.clevertec.check.domain.model.valueobject.CardNumber;

import java.math.BigDecimal;

public class NullDiscountCard implements DiscountCard{
    @Override
    public CardId getId() {
        return new CardId(0);
    }

    @Override
    public CardNumber getCardNumber() {
        return new CardNumber(0);
    }

    @Override
    public boolean isValid() {
        return false;
    }

    @Override
    public BigDecimal getDiscountAmount() {
        return BigDecimal.ZERO;
    }

    @Override
    public void addCardNumber(CardNumber cardNumber) {

    }

    @Override
    public void addDiscountAmount(BigDecimal discountAmount) {

    }

    @Override
    public String toString() {
        return getId().toString();
    }
}
