package ru.clevertec.check.domain.model.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
    @DisplayName("Should return the correct card ID")
    void getId() {
        Assertions.assertAll(
                () -> assertNotNull(discountCard.getId()),
                () -> assertEquals(new CardId(1), discountCard.getId())
        );
    }

    @Test
    @DisplayName("Should return the correct card number")
    void getCardNumber() {
        CardNumber cardNumber = new CardNumber(1111);
        discountCard.addCardNumber(cardNumber);

        Assertions.assertAll(
                () -> assertNotNull(discountCard.getCardNumber()),
                () -> assertEquals(cardNumber, discountCard.getCardNumber())
        );
    }

    @Test
    @DisplayName("Should validate the discount card")
    void isValid() {
        assertTrue(discountCard.isValid());
    }

    @Test
    @DisplayName("Should return the correct discount amount")
    void getDiscountAmount() {
        assertEquals(BigDecimal.TEN, discountCard.getDiscountAmount());
    }

    @Test
    @DisplayName("Should correctly add and return the card number")
    void addCardNumber() {
        CardNumber cardNumber = new CardNumber(1111);
        discountCard.addCardNumber(cardNumber);
        assertEquals(cardNumber, discountCard.getCardNumber());
    }

    @Test
    @DisplayName("Should add discount amount")
    void addDiscountAmount() {
        BigDecimal discountAmount = BigDecimal.TEN;
        discountCard.addDiscountAmount(discountAmount);
        assertEquals(discountAmount, discountCard.getDiscountAmount());
    }

    @Test
    @DisplayName("Should return the correct discount amount")
    void testToString() {
        assertEquals("CardId[id=1]", discountCard.toString());
    }
}