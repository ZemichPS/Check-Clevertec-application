package ru.clevertec.check.domain.model.entity;

import ru.clevertec.check.domain.model.valueobject.CardId;
import ru.clevertec.check.domain.model.valueobject.CardNumber;
import ru.clevertec.check.domain.specification.DiscountCardNumberSpecification;

import java.math.BigDecimal;

public class RealDiscountCard implements DiscountCard{

    private final CardId id;

    private CardNumber number;

    private BigDecimal discountAmount;

    public RealDiscountCard(CardId id) {
        this.id = id;
    }

    public RealDiscountCard(CardId id,
                            BigDecimal discountAmount) {
        this.id = id;
        this.discountAmount = discountAmount;
    }


    @Override
    public CardId getId() {
        return id;
    }

    @Override
    public CardNumber getCardNumber() {
        return number;
    }

    @Override
    public boolean isValid() {
        return true;
    }

    public void addDiscountAmount(BigDecimal percentage) {
        discountAmount = percentage;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void addCardNumber(CardNumber cardNumber){
        DiscountCardNumberSpecification spec = new DiscountCardNumberSpecification();
        spec.check(cardNumber);
        this.number = cardNumber;
    }
}
