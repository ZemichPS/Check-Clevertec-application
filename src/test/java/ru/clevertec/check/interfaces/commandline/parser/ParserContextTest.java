package ru.clevertec.check.interfaces.commandline.parser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.clevertec.check.domain.model.valueobject.CardNumber;
import ru.clevertec.check.domain.model.valueobject.ProductId;

import java.math.BigDecimal;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ParserContextTest {

    private ParserContext parserContext;

    @BeforeEach
    void setUp() {
        parserContext = new ParserContext();
        parserContext.addParser(new BalanceDebitCardRegexParser());
        parserContext.addParser(new DiscountCardNumberParser());
        parserContext.addParser(new IdQuantityToMapRegexParser());
    }

    @Test
    @DisplayName("Parse arguments with balance, discount card, and product ID")
    void testParseArgumentsWithAllFields() {
        String[] args = {"balanceDebitCard=100.50", "discountCard=12345", "123-5", "456-3"};
        ArgumentParsingContext context = parserContext.parseArguments(args);

        assertEquals(new BigDecimal("100.50"), context.getBalanceDebitCard().orElseThrow());
        assertEquals(new CardNumber(12345), context.getCardNumber().orElseThrow());

        Map<ProductId, Integer> productIdQuantityMap = context.getProductIdQuantityMap();
        assertEquals(2, productIdQuantityMap.size());
        assertEquals(5, productIdQuantityMap.get(new ProductId(123)));
        assertEquals(3, productIdQuantityMap.get(new ProductId(456)));
    }

    @Test
    @DisplayName("Parse arguments with only balance and discount card")
    void testParseArgumentsWithBalanceAndDiscountCard() {
        String[] args = {"balanceDebitCard=50.00", "discountCard=54321"};
        ArgumentParsingContext context = parserContext.parseArguments(args);

        assertEquals(new BigDecimal("50.00"), context.getBalanceDebitCard().orElseThrow());
        assertEquals(new CardNumber(54321), context.getCardNumber().orElseThrow());
        assertTrue(context.getProductIdQuantityMap().isEmpty());
    }

    @Test
    @DisplayName("Handle invalid arguments")
    void testHandleInvalidArguments() {
        String[] args = {"invalidArgument", "balanceDebitCard=invalid", "123-abc"};
        ArgumentParsingContext context = parserContext.parseArguments(args);

        assertTrue(context.getBalanceDebitCard().isEmpty());
        assertTrue(context.getCardNumber().isEmpty());
        assertTrue(context.getProductIdQuantityMap().isEmpty());
    }
}
