package ru.clevertec.check.domain.policy.discountpolicy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.clevertec.check.domain.model.valueobject.DiscountCard;
import ru.clevertec.check.domain.model.valueobject.OrderItemDto;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DiscountCardPolicyTest {

    @Test
    @DisplayName("Calculate discount amount correctly for a valid discount card")
    void testApplyDiscount() {
        // Arrange
        DiscountCard discountCard = new DiscountCard(true, new BigDecimal("10.00")); // 10% discount
        OrderItemDto orderItem = new OrderItemDto(discountCard, 2, new BigDecimal("50.00"));
        DiscountCardPolicy policy = new DiscountCardPolicy();

        // Act
        BigDecimal discountAmount = policy.apply(orderItem);

        // Assert
        BigDecimal expectedDiscountAmount = new BigDecimal("10.00"); // (50.00 * 2) * (10.00 / 100)
        assertEquals(expectedDiscountAmount, discountAmount);
    }

    @Test
    @DisplayName("Return zero discount amount for an invalid discount card")
    void testApplyDiscountWithInvalidCard() {
        // Arrange
        DiscountCard discountCard = new DiscountCard(false, new BigDecimal("10.00")); // Invalid card
        OrderItemDto orderItem = new OrderItemDto(discountCard, 2, new BigDecimal("50.00"));
        DiscountCardPolicy policy = new DiscountCardPolicy();

        // Act
        BigDecimal discountAmount = policy.apply(orderItem);

        // Assert
        assertEquals(BigDecimal.ZERO, discountAmount);
    }
}
