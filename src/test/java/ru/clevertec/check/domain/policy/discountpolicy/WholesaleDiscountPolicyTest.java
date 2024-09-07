package ru.clevertec.check.domain.policy.discountpolicy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.clevertec.check.domain.model.dto.OrderItemDto;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WholesaleDiscountPolicyTest {

    @Test
    @DisplayName("Apply wholesale discount correctly for quantities above threshold")
    void testApplyWholesaleDiscount() {
        // Arrange
        BigDecimal threshold = new BigDecimal("10");
        BigDecimal discountPercentage = new BigDecimal("15.00"); // 15% discount
        WholesaleDiscountPolicy policy = new WholesaleDiscountPolicy(threshold, discountPercentage);
        OrderItemDto orderItem = new OrderItemDto(null, 15, new BigDecimal("100.00")); // Quantity exceeds threshold

        // Act
        BigDecimal discountAmount = policy.apply(orderItem);

        // Assert
        BigDecimal expectedDiscountAmount = new BigDecimal("150.00"); // 100.00 * 15 * (15.00 / 100)
        assertEquals(expectedDiscountAmount, discountAmount);
    }

    @Test
    @DisplayName("Return zero discount amount for quantities below threshold")
    void testApplyWholesaleDiscountBelowThreshold() {
        // Arrange
        BigDecimal threshold = new BigDecimal("10");
        BigDecimal discountPercentage = new BigDecimal("15.00"); // 15% discount
        WholesaleDiscountPolicy policy = new WholesaleDiscountPolicy(threshold, discountPercentage);
        OrderItemDto orderItem = new OrderItemDto(null, 5, new BigDecimal("100.00")); // Quantity below threshold

        // Act
        BigDecimal discountAmount = policy.apply(orderItem);

        // Assert
        assertEquals(BigDecimal.ZERO, discountAmount);
    }
}
