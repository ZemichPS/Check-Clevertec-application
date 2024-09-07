package ru.clevertec.check.domain.specification;

import org.junit.jupiter.api.Test;
import ru.clevertec.check.domain.model.exception.NotEnoughMoneyException;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class PositiveDebitCardBalanceSpecificationTest {

    private final PositiveDebitCardBalanceSpecification specification = new PositiveDebitCardBalanceSpecification();

    @Test
    void testIsSatisfiedBy_whenBalanceIsPositive_ShouldReturnTrue() {
        BigDecimal positiveBalance = new BigDecimal("100.00");
        assertTrue(specification.isSatisfiedBy(positiveBalance));
    }

    @Test
    void testIsSatisfiedBy_whenBalanceIsZero_ShouldReturnFalse() {
        BigDecimal zeroBalance = BigDecimal.ZERO;
        assertFalse(specification.isSatisfiedBy(zeroBalance));
    }

    @Test
    void testIsSatisfiedBy_whenBalanceIsNegative_ShouldReturnFalse() {
        BigDecimal negativeBalance = new BigDecimal("-100.00");
        assertFalse(specification.isSatisfiedBy(negativeBalance));
    }

    @Test
    void testCheck_whenBalanceIsPositive_ShouldNotThrowException() {
        BigDecimal positiveBalance = new BigDecimal("100.00");
        assertDoesNotThrow(() -> specification.check(positiveBalance));
    }

    @Test
    void testCheck_whenBalanceIsZero_ShouldThrowNotEnoughMoneyException() {
        BigDecimal zeroBalance = BigDecimal.ZERO;
        NotEnoughMoneyException exception = assertThrows(NotEnoughMoneyException.class,
                () -> specification.check(zeroBalance));
        assertEquals("The debit card balance must be passed in arguments and be positive", exception.getMessage());
    }

    @Test
    void testCheck_whenBalanceIsNegative_ShouldThrowNotEnoughMoneyException() {
        BigDecimal negativeBalance = new BigDecimal("-100.00");
        NotEnoughMoneyException exception = assertThrows(NotEnoughMoneyException.class,
                () -> specification.check(negativeBalance));
        assertEquals("The debit card balance must be passed in arguments and be positive", exception.getMessage());
    }
}
