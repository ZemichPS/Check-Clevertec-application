package ru.clevertec.check.domain.policy.discountpolicy;

import ru.clevertec.check.domain.model.dto.OrderItemDto;
import ru.clevertec.check.domain.model.entity.DiscountCard;
import ru.clevertec.check.domain.policy.discountpolicy.shared.DiscountPolicy;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class DiscountCardPolicy implements DiscountPolicy<OrderItemDto> {

    @Override
    public boolean isApplicable(OrderItemDto applicable) {
        DiscountCard discountCard = applicable.discountCard();
        return discountCard.isValid();
    }

    @Override
    public BigDecimal apply(OrderItemDto applicable) {
        if(!isApplicable(applicable)) return BigDecimal.ZERO;
        Integer quantity = applicable.quantity();
        BigDecimal price = applicable.price();
        DiscountCard discountCard = applicable.discountCard();
        BigDecimal discountAmount = discountCard.getDiscountAmount();

        return price.multiply(new BigDecimal(quantity))
                .multiply(discountAmount)
                .divide(new BigDecimal(100), RoundingMode.DOWN)
                .setScale(2, RoundingMode.DOWN);
    }
}
