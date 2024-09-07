package ru.clevertec.check.domain.specification;

import org.junit.jupiter.api.Test;
import ru.clevertec.check.domain.model.exception.GenericSpecificationException;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class PositiveCheckItemPriceSpecificationTest {


    private final PositiveCheckItemPriceSpecification specification = new PositiveCheckItemPriceSpecification();

    @Test
    void testCheck_whenPriceIsNegative_ShouldThrowGenericSpecificationException() {
        BigDecimal price = BigDecimal.valueOf(-1);
        assertThrows(GenericSpecificationException.class, () -> specification.check(price));
    }

    @Test
    void testCheck_whenPriceIsPositive_ShouldPass() {
        BigDecimal price = BigDecimal.ONE;
        assertDoesNotThrow(() -> specification.check(price));
    }

    @Test
    void testIsSatisfiedBy_whenPriceIsNegative_ShouldReturnFalse() {
        BigDecimal price = BigDecimal.valueOf(-1);
        assertFalse(specification.isSatisfiedBy(price));
    }

    @Test
    void testIsSatisfiedBy_whenPriceIsPositive_ShouldReturnTrue() {
        BigDecimal price = BigDecimal.ONE;
        assertTrue(specification.isSatisfiedBy(price));
    }
}