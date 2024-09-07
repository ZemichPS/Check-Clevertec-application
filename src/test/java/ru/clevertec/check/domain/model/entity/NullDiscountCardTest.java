package ru.clevertec.check.domain.model.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.clevertec.check.domain.model.valueobject.CardId;
import ru.clevertec.check.domain.model.valueobject.CardNumber;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class NullDiscountCardTest {

    private final NullDiscountCard discountCard = new NullDiscountCard();

    @Test
    @DisplayName("Should return default ID for NullDiscountCard")
    void getId() {
        Assertions.assertAll(
                () -> assertNotNull(discountCard.getId()),
                () -> assertDoesNotThrow(discountCard::getId),
                () -> assertEquals(new CardId(0), discountCard.getId())
        );
    }

    @Test
    @DisplayName("Should return default CardNumber for NullDiscountCard")
    void getCardNumber() {
        Assertions.assertAll(
                () -> assertNotNull(discountCard.getCardNumber()),
                () -> assertDoesNotThrow(discountCard::getCardNumber),
                () -> assertEquals(new CardNumber(0), discountCard.getCardNumber())
        );
    }

    @Test
    @DisplayName("Should return false for validity check on NullDiscountCard")
    void isValid() {
        assertFalse(discountCard.isValid());
    }

    @Test
    @DisplayName("Should return zero discount amount for NullDiscountCard")
    void getDiscountAmount() {
        assertEquals(BigDecimal.ZERO, discountCard.getDiscountAmount());
    }

    @Test
    @DisplayName("Should not change card number after addition in NullDiscountCard")
    void addCardNumber() {
        CardNumber cardNumber = new CardNumber(1111);
        discountCard.addCardNumber(cardNumber);
        assertNotEquals(cardNumber, discountCard.getCardNumber());
    }

    @Test
    @DisplayName("Should not change discount amount after addition in NullDiscountCard")
    void addDiscountAmount() {
        BigDecimal discountAmount = BigDecimal.TEN;
        discountCard.addDiscountAmount(discountAmount);
        assertNotEquals(discountAmount, discountCard.getDiscountAmount());
    }

    @Test
    @DisplayName("Should return correct string representation of NullDiscountCard")
    void testToString() {
        assertEquals("CardId[id=0]", discountCard.toString());
    }
}