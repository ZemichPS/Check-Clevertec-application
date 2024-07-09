package ru.clevertec.check.domain.service;

import ru.clevertec.check.domain.model.dto.OrderItemDto;
import ru.clevertec.check.domain.policy.discountpolicy.DiscountCardPolicy;
import ru.clevertec.check.domain.policy.discountpolicy.shared.DiscountPolicy;
import ru.clevertec.check.domain.policy.discountpolicy.WholesaleDiscountPolicy;

import java.math.BigDecimal;

public class DiscountService {
    private final DiscountPolicy<OrderItemDto> wholesaleDiscountPolicy = new WholesaleDiscountPolicy();
    private final DiscountPolicy<OrderItemDto> discountCardPolicy = new DiscountCardPolicy();

    public BigDecimal computeDiscount(OrderItemDto item) {
        if (wholesaleDiscountPolicy.isApplicable(item)) {
            return wholesaleDiscountPolicy.apply(item);
        } else if (discountCardPolicy.isApplicable(item)) {
            return discountCardPolicy.apply(item);
        }
        return BigDecimal.ZERO;
    }
}
