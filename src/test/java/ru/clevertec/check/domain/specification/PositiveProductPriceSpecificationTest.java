package ru.clevertec.check.domain.specification;

import org.junit.jupiter.api.Test;
import ru.clevertec.check.domain.model.exception.GenericSpecificationException;
import ru.clevertec.check.domain.model.valueobject.Price;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class PositiveProductPriceSpecificationTest {

    private final PositiveProductPriceSpecification specification = new PositiveProductPriceSpecification();

    @Test
    void testIsSatisfiedBy_whenPriceIsPositive_ShouldReturnTrue() {
        Price positivePrice = new Price(BigDecimal.valueOf(10.00));
        assertTrue(specification.isSatisfiedBy(positivePrice));
    }

    @Test
    void testIsSatisfiedBy_whenPriceIsZero_ShouldReturnFalse() {
        Price zeroPrice = new Price(BigDecimal.ZERO);
        assertFalse(specification.isSatisfiedBy(zeroPrice));
    }

    @Test
    void testIsSatisfiedBy_whenPriceIsNegative_ShouldReturnFalse() {
        Price negativePrice = new Price(BigDecimal.valueOf(-10.00));
        assertFalse(specification.isSatisfiedBy(negativePrice));
    }

    @Test
    void testCheck_whenPriceIsPositive_ShouldNotThrowException() {
        Price positivePrice = new Price(BigDecimal.valueOf(10.00));
        assertDoesNotThrow(() -> specification.check(positivePrice));
    }

    @Test
    void testCheck_whenPriceIsZero_ShouldThrowGenericSpecificationException() {
        Price zeroPrice = new Price(BigDecimal.ZERO);
        GenericSpecificationException exception = assertThrows(GenericSpecificationException.class,
                () -> specification.check(zeroPrice));
        assertEquals("The price of the product should be positive. Current price: 0", exception.getMessage());
    }

    @Test
    void testCheck_whenPriceIsNegative_ShouldThrowGenericSpecificationException() {
        Price negativePrice = new Price(BigDecimal.valueOf(-10.00));
        GenericSpecificationException exception = assertThrows(GenericSpecificationException.class,
                () -> specification.check(negativePrice));
        assertEquals("The price of the product should be positive. Current price: -10.0", exception.getMessage());
    }
}
