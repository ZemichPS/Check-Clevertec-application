package ru.clevertec.check.domain.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.check.domain.model.entity.RealDiscountCard;
import ru.clevertec.check.domain.model.valueobject.CardId;
import ru.clevertec.check.domain.model.valueobject.CardNumber;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class DiscountCardServiceTest {

    @Test
    void testFindByCardNumber_whenCardNumberExists_ShouldReturnDiscountCard() {
        CardNumber cardNumber = new CardNumber(1111);
        RealDiscountCard discountCard = new RealDiscountCard(new CardId(1), BigDecimal.ONE);
        discountCard.addCardNumber(cardNumber);
        List<RealDiscountCard> discountCards = List.of(discountCard);
        Optional<RealDiscountCard> result = DiscountCardService.findByCardNumber(discountCards, cardNumber);
        assertTrue(result.isPresent());
        assertEquals(discountCard, result.get());
    }

    @Test
    void testFindByCardNumber_whenCardNumberDoesNotExist_ShouldReturnEmpty() {
        CardNumber cardNumber = new CardNumber(1111);
        RealDiscountCard discountCard = new RealDiscountCard(new CardId(1), BigDecimal.ONE);
        discountCard.addCardNumber(new CardNumber(1564));
        List<RealDiscountCard> discountCards = List.of(discountCard);
        Optional<RealDiscountCard> result = DiscountCardService.findByCardNumber(discountCards, cardNumber);
        assertFalse(result.isPresent());
    }

    @Test
    void testFindByCardNumber_whenListIsEmpty_ShouldReturnEmpty() {
        CardNumber cardNumber = new CardNumber(1111);
        RealDiscountCard discountCard = new RealDiscountCard(new CardId(1), BigDecimal.ONE);
        discountCard.addCardNumber(new CardNumber(1564));
        List<RealDiscountCard> discountCards = List.of(discountCard);
        Optional<RealDiscountCard> result = DiscountCardService.findByCardNumber(discountCards, cardNumber);
        assertTrue(result.isEmpty());
    }

}
