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
class DiscountCardPolicyTest {

    @InjectOrderItem(quantity = 15)
    private OrderItemDto orderItem;

    private final DiscountCardPolicy policy = new DiscountCardPolicy();

    @Test
    @DisplayName("Calculate discount amount correctly for a valid discount card")
    void testApplyDiscount() {
        BigDecimal discountAmount = policy.apply(orderItem);
        assertEquals(BigDecimal.valueOf(2.22), discountAmount);
    }

    @Test
    @DisplayName("Return zero discount amount for an invalid discount card")
    void testApplyDiscountWithInvalidCard() {
        NullDiscountCard invalidDiscountCard = new NullDiscountCard();

        OrderItemDto orderItem = new OrderItemDto(
                invalidDiscountCard,
                SaleConditionType.USUAL_PRICE,
                15,
                BigDecimal.valueOf(1.48),
                "Milk 1l."
        );
        BigDecimal discountAmount = policy.apply(orderItem);
        assertEquals(BigDecimal.ZERO, discountAmount);
    }

    @Test
    void isApplicableTest_whenDiscountCardIsNull_returnFalse(){
        OrderItemDto orderItem = new OrderItemDto(
                new NullDiscountCard(),
                SaleConditionType.USUAL_PRICE,
                15,
                BigDecimal.valueOf(1.48),
                "Milk 1l."
        );
        assertFalse(policy.isApplicable(orderItem));
    }

    void isApplicableTest_whenDiscountCardIsReal_returnFalse(){
        assertTrue(policy.isApplicable(orderItem));
    }
}
