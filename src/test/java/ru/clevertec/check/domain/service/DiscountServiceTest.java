package ru.clevertec.check.domain.service;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.check.domain.model.dto.OrderItemDto;
import ru.clevertec.check.domain.model.entity.NullDiscountCard;
import ru.clevertec.check.domain.model.entity.RealDiscountCard;
import ru.clevertec.check.domain.model.valueobject.*;
import ru.clevertec.check.domain.policy.discountpolicy.shared.DiscountPolicy;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DiscountServiceTest {

    DiscountService discountService = new DiscountService();

    @Test
    void testComputeDiscount_whenWholesaleDiscountPolicyApplicable_ShouldApplyWholesaleDiscount() {
        OrderItemDto orderItem = new OrderItemDto(
                new RealDiscountCard(new CardId(1111), BigDecimal.TEN),
                SaleConditionType.WHOLESALE,
                10,
                BigDecimal.ONE,
                "Milk 1l");
        BigDecimal discount = discountService.computeDiscount(orderItem);
        assertEquals(BigDecimal.valueOf(1).setScale(2, RoundingMode.DOWN), discount);
    }

    @Test
    void testComputeDiscount_whenDiscountCardPolicyApplicable_ShouldApplyDiscountCardPolicy() {
        OrderItemDto orderItem = new OrderItemDto(
                new RealDiscountCard(new CardId(1111), BigDecimal.valueOf(5)),
                SaleConditionType.USUAL_PRICE,
                10,
                BigDecimal.valueOf(5.60),
                "Milk 1l");
        BigDecimal discount = discountService.computeDiscount(orderItem);
        assertEquals(BigDecimal.valueOf(2.80).setScale(2, RoundingMode.DOWN), discount);
    }

    @Test
    void testComputeDiscount_whenNoDiscountPoliciesApplicable_ShouldReturnZero() {
        OrderItemDto orderItem = new OrderItemDto(
                new NullDiscountCard(),
                SaleConditionType.USUAL_PRICE,
                10,
                BigDecimal.ONE,
                "Milk 1l");
        BigDecimal discount = discountService.computeDiscount(orderItem);
        assertEquals(BigDecimal.ZERO, discount);
    }
}
