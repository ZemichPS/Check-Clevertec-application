package ru.clevertec.check.domain.policy.discountpolicy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.clevertec.check.domain.model.dto.OrderItemDto;
import ru.clevertec.check.domain.model.entity.DiscountCard;
import ru.clevertec.check.domain.model.entity.NullDiscountCard;
import testconfig.InjectOrderItem;
import testconfig.InjectOrderItemProcessor;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({InjectOrderItemProcessor.class})
class CardDiscountAccrualPolicyTest {

    @InjectOrderItem(cardNumber = 5131)
    private OrderItemDto orderItemDto;
    private CardDiscountAccrualPolicy policy = new CardDiscountAccrualPolicy();

    @Test
    void testApply_ifDiscountCardIsValid_setDiscountAmount() {
        DiscountCard realDiscountCard = orderItemDto.discountCard();
        policy.apply(realDiscountCard);
        assertEquals(BigDecimal.TWO, realDiscountCard.getDiscountAmount());
    }

    @Test
    void testApply_ifDiscountCardIsInvalid_doesNotApplyDiscount() {
        DiscountCard nullDiscountCard = new NullDiscountCard();
        policy.apply(nullDiscountCard);
        assertEquals(BigDecimal.ZERO, nullDiscountCard.getDiscountAmount());
    }

    @Test
    void testIsApplicable_ifDiscountCardIsInvalid_returnFalse() {
        DiscountCard nullDiscountCard = new NullDiscountCard();
        assertFalse(nullDiscountCard.isValid());
    }

    @Test
    void testIsApplicable_ifDiscountCardIsIValid_returnTrue() {
        DiscountCard nullDiscountCard = orderItemDto.discountCard();
        assertTrue(nullDiscountCard.isValid());
    }
}
