package ru.clevertec.check.interfaces.commandline.parser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.clevertec.check.interfaces.commandline.parser.BalanceDebitCardRegexParser;
import ru.clevertec.check.interfaces.commandline.parser.ArgumentParsingContext;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class BalanceDebitCardRegexParserTest {

    private BalanceDebitCardRegexParser parser;
    private ArgumentParsingContext context;

    @BeforeEach
    void setUp() {
        parser = new BalanceDebitCardRegexParser();
        context = new ArgumentParsingContext();
    }

    @Test
    @DisplayName("Parse valid balance debit card argument")
    void testParseValidBalanceDebitCard() {
        String arg = "balanceDebitCard=123.456";
        parser.parse(arg, context);
        assertEquals(new BigDecimal("123.45"), context.getBalanceDebitCard().orElse(null));
    }

    @Test
    @DisplayName("Parse valid negative balance debit card argument")
    void testParseNegativeBalanceDebitCard() {
        String arg = "balanceDebitCard=-123.456";
        parser.parse(arg, context);
        assertEquals(new BigDecimal("-123.45"), context.getBalanceDebitCard().orElse(null));
    }

    @Test
    @DisplayName("Parse valid integer balance debit card argument")
    void testParseIntegerBalanceDebitCard() {
        String arg = "balanceDebitCard=123";
        parser.parse(arg, context);
        assertEquals(new BigDecimal("123.00"), context.getBalanceDebitCard().orElse(null));
    }

    @Test
    @DisplayName("Do not parse invalid balance debit card argument")
    void testDoNotParseInvalidBalanceDebitCard() {
        String arg = "balanceDebitCard=abc123";
        parser.parse(arg, context);
        assertTrue(context.getBalanceDebitCard().isEmpty());
    }

    @Test
    @DisplayName("Do not parse when no static prefix")
    void testDoNotParseWithoutStaticPrefix() {
        String arg = "debitCard=123.45";
        parser.parse(arg, context);
        assertTrue(context.getBalanceDebitCard().isEmpty());
    }
}
