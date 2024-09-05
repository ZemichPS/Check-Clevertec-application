package ru.clevertec.check.domain.model.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.clevertec.check.domain.model.valueobject.CardId;
import ru.clevertec.check.domain.model.valueobject.CardNumber;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class RealDiscountCardTest {

    RealDiscountCard discountCard;

    @BeforeEach
    void setup() {
        discountCard = new RealDiscountCard(new CardId(1), BigDecimal.TEN);
    }

    @Test
    void getId() {
        Assertions.assertAll(
                () -> assertNotNull(discountCard.getId()),
                () -> assertDoesNotThrow(() -> discountCard.getId()),
                () -> assertEquals(new CardId(1), discountCard.getId())
        );
    }

    @Test
    void getCardNumber() {
        CardNumber cardNumber = new CardNumber(1111);
        discountCard.addCardNumber(cardNumber);

        Assertions.assertAll(
                () -> assertNotNull(discountCard.getCardNumber()),
                () -> assertDoesNotThrow(() -> discountCard.getCardNumber()),
                () -> assertEquals(cardNumber, discountCard.getCardNumber())
        );
    }

    @Test
    void isValid() {
        assertTrue(discountCard.isValid());
    }

    @Test
    void getDiscountAmount() {
        assertEquals(BigDecimal.TEN, discountCard.getDiscountAmount());
    }

    @Test
    void addCardNumber() {
        CardNumber cardNumber = new CardNumber(1111);
        discountCard.addCardNumber(cardNumber);
        Assertions.assertAll(
                () -> assertDoesNotThrow(() -> discountCard.addCardNumber(cardNumber)),
                () -> assertEquals(cardNumber, discountCard.getCardNumber())
        );
    }

    @Test
    void addDiscountAmount() {
        BigDecimal discountAmount = BigDecimal.TEN;
        discountCard.addDiscountAmount(discountAmount);
        Assertions.assertAll(
                () -> assertDoesNotThrow(() -> discountCard.addDiscountAmount(discountAmount)),
                () -> assertEquals(discountAmount, discountCard.getDiscountAmount())
        );
    }

    @Test
    void testToString() {
        String result = discountCard.getId().toString();
        assertEquals(result, discountCard.toString());
    }
}