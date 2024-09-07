package ru.clevertec.check.domain.policy.discountpolicy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.clevertec.check.domain.model.entity.Check;
import ru.clevertec.check.domain.model.entity.factoty.CheckFactory;
import ru.clevertec.check.domain.model.valueobject.CardNumber;
import ru.clevertec.check.domain.model.valueobject.CheckItem;
import ru.clevertec.check.domain.model.valueobject.ProductId;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CardDiscountAccrualPolicyTest {

    @Test
    @DisplayName("Apply discount to Check based on CardNumber")
    void testApplyDiscount() {
        // Arrange
        CardNumber cardNumber = new CardNumber(123456);
        BigDecimal discountRate = new BigDecimal("0.10"); // 10% discount
        CardDiscountAccrualPolicy policy = new CardDiscountAccrualPolicy(cardNumber, discountRate);

        CheckItem checkItem = new CheckItem(new ProductId(1), 2);
        BigDecimal itemPrice = new BigDecimal("50.00");
        checkItem.setPrice(itemPrice);

        List<CheckItem> checkItems = Collections.singletonList(checkItem);
        Check check = CheckFactory.getNewFromCheckItems(checkItems);

        // Act
        BigDecimal totalPriceBeforeDiscount = check.computeAndGetTotalPrices().totalPrice();
        policy.apply(check);
        BigDecimal totalPriceAfterDiscount = check.getDiscountCard().getDiscountAmount();

        // Assert
        assertEquals(totalPriceBeforeDiscount.multiply(BigDecimal.ONE.subtract(discountRate)), totalPriceAfterDiscount);
    }
}
