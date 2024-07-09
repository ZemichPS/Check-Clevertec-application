package ru.clevertec.check.domain.policy.discountpolicy;

import ru.clevertec.check.domain.model.dto.OrderItemDto;
import ru.clevertec.check.domain.model.valueobject.SaleConditionType;
import ru.clevertec.check.domain.policy.discountpolicy.shared.DiscountPolicy;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class WholesaleDiscountPolicy implements DiscountPolicy<OrderItemDto> {
    private final Integer MIN_REQUIRED_NUMBER_IN_POSITION = 5;
    private final BigDecimal WHOLESALE_DISCOUNT_PERCENTAGE = new BigDecimal(10);
    private Integer productQuantity;
    private BigDecimal productPrice;

    @Override
    public boolean isApplicable(OrderItemDto applicable) {
        this.productQuantity = applicable.quantity();
        this.productPrice = applicable.price();
        SaleConditionType saleConditionType = applicable.saleConditionType();

        return productQuantity >= MIN_REQUIRED_NUMBER_IN_POSITION && saleConditionType.equals(SaleConditionType.WHOLESALE);
    }

    @Override
    public BigDecimal apply(OrderItemDto applicable) {
        if (!this.isApplicable(applicable)) return BigDecimal.ZERO;

        return productPrice
                .multiply(new BigDecimal(productQuantity))
                .multiply(WHOLESALE_DISCOUNT_PERCENTAGE)
                .divide(new BigDecimal(100), 2, RoundingMode.DOWN);
    }
}


