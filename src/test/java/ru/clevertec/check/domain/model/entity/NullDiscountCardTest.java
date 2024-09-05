package ru.clevertec.check.domain.model.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.clevertec.check.domain.model.valueobject.CardId;
import ru.clevertec.check.domain.model.valueobject.CardNumber;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class NullDiscountCardTest {

    NullDiscountCard discountCard;

    @BeforeEach
    void setup() {
        discountCard = new NullDiscountCard();
    }

    @Test
    void getId() {
        Assertions.assertAll(
                () -> assertNotNull(discountCard.getId()),
                () -> assertDoesNotThrow(() -> discountCard.getId()),
                () -> assertEquals(new CardId(0), discountCard.getId())
        );
    }

    @Test
    void getCardNumber() {
        Assertions.assertAll(
                () -> assertNotNull(discountCard.getCardNumber()),
                () -> assertDoesNotThrow(() -> discountCard.getCardNumber()),
                () -> assertEquals(new CardNumber(0), discountCard.getCardNumber())
        );
    }

    @Test
    void isValid() {
        assertFalse(discountCard.isValid());
    }

    @Test
    void getDiscountAmount() {
        assertEquals(BigDecimal.ZERO, discountCard.getDiscountAmount());
    }

    @Test
    void addCardNumber() {
        CardNumber cardNumber = new CardNumber(1111);
        discountCard.addCardNumber(cardNumber);

        Assertions.assertAll(
                () -> assertDoesNotThrow(() -> discountCard.addCardNumber(cardNumber)),
                () -> assertNotEquals(cardNumber, discountCard.getCardNumber())
        );
    }

    @Test
    void addDiscountAmount() {
        BigDecimal discountAmount = BigDecimal.TEN;
        discountCard.addDiscountAmount(discountAmount);

        Assertions.assertAll(
                () -> assertDoesNotThrow(() -> discountCard.addDiscountAmount(discountAmount)),
                () -> assertNotEquals(discountAmount, discountCard.getDiscountAmount())
        );

    }

    @Test
    void testToString() {
        String result = discountCard.getId().toString();
        assertEquals(result, discountCard.toString());
    }
}