package ru.clevertec.check.domain.policy.discountpolicy;

import ru.clevertec.check.domain.model.entity.DiscountCard;
import ru.clevertec.check.domain.policy.discountpolicy.shared.DiscountPolicy;

import java.math.BigDecimal;

public class CardDiscountAccrualPolicy implements DiscountPolicy<DiscountCard> {
    private final BigDecimal DISCOUNT_AMOUNT_FOR_OTHER_CARD = BigDecimal.TWO;

    @Override
    public boolean isApplicable(DiscountCard applicable) {
        return applicable.isValid();
    }

    @Override
    public BigDecimal apply(DiscountCard discountCard) {
        discountCard.addDiscountAmount(DISCOUNT_AMOUNT_FOR_OTHER_CARD);
        return DISCOUNT_AMOUNT_FOR_OTHER_CARD;
    }
}
