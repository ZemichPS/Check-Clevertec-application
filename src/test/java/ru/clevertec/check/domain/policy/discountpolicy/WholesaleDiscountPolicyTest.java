package ru.clevertec.check.domain.policy.discountpolicy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.clevertec.check.domain.model.dto.OrderItemDto;
import ru.clevertec.check.domain.model.entity.NullDiscountCard;
import ru.clevertec.check.domain.model.valueobject.SaleConditionType;
import testconfig.InjectOrderItem;
import testconfig.InjectOrderItemProcessor;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(InjectOrderItemProcessor.class)
class WholesaleDiscountPolicyTest {

    @InjectOrderItem(quantity = 20, wholeSale = true)
    private OrderItemDto orderItem;
    private WholesaleDiscountPolicy policy = new WholesaleDiscountPolicy();

    @Test
    @DisplayName("Apply wholesale discount correctly for quantities above threshold")
    void testApplyWholesaleDiscount() {
        BigDecimal discountAmount = policy.apply(orderItem);
        BigDecimal expectedDiscountAmount = new BigDecimal("2.96");
        assertEquals(expectedDiscountAmount, discountAmount);
    }

    @Test
    @DisplayName("Return zero discount amount for quantities below threshold")
    void testApplyWholesaleDiscountBelowThreshold() {
        OrderItemDto orderItem = new OrderItemDto(
                new NullDiscountCard(),
                SaleConditionType.WHOLESALE,
                3,
                BigDecimal.valueOf(1.48),
                "Milk 1l."
        );
        BigDecimal discountAmount = policy.apply(orderItem);
        assertEquals(BigDecimal.ZERO, discountAmount);
    }

    @Test
    @DisplayName("Return false for quantities below threshold")
    void testIsApplicableWholesaleDiscountBelowThreshold() {
        OrderItemDto orderItem = new OrderItemDto(
                new NullDiscountCard(),
                SaleConditionType.WHOLESALE,
                3,
                BigDecimal.valueOf(1.48),
                "Milk 1l."
        );
        assertFalse(policy.isApplicable(orderItem));
    }

    @Test
    @DisplayName("Return true for quantities below threshold")
    void testIsApplicableWholesaleDiscountUnderThreshold() {
        assertTrue(policy.isApplicable(orderItem));
    }


}
