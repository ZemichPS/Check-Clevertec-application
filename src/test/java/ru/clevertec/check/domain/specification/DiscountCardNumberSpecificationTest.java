package ru.clevertec.check.domain.specification;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.clevertec.check.domain.model.exception.GenericSpecificationException;
import ru.clevertec.check.domain.model.valueobject.CardNumber;

import static org.junit.jupiter.api.Assertions.*;

class DiscountCardNumberSpecificationTest {

    private final DiscountCardNumberSpecification specification = new DiscountCardNumberSpecification();

    @Test
    void shouldPassWhenCardNumberHasValidLength() {
        CardNumber validCard = new CardNumber(1234);
        assertDoesNotThrow(() -> specification.check(validCard));
    }

    @Test
    void shouldFailWhenCardNumberHasInvalidLength() {
        CardNumber invalidCard = new CardNumber(12345);
        Exception exception = assertThrows(GenericSpecificationException.class, () -> specification.check(invalidCard));
        assertEquals("Length discount card number must be 4, but current card number 5", exception.getMessage());
    }

    @Test
    void shouldReturnTrueWhenCardNumberIsValid() {
        CardNumber validCard = new CardNumber(1234);
        assertTrue(specification.isSatisfiedBy(validCard));
    }

    @Test
    void shouldReturnFalseWhenCardNumberIsInvalid() {
        CardNumber invalidCard = new CardNumber(123);
        assertFalse(specification.isSatisfiedBy(invalidCard));
    }
}
