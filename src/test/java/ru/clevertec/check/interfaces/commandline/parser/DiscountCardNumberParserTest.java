package ru.clevertec.check.interfaces.commandline.parser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.clevertec.check.domain.model.valueobject.CardNumber;

import static org.junit.jupiter.api.Assertions.*;

class DiscountCardNumberParserTest {

    private DiscountCardNumberParser parser;
    private ArgumentParsingContext context;

    @BeforeEach
    void setUp() {
        parser = new DiscountCardNumberParser();
        context = new ArgumentParsingContext();
    }

    @Test
    @DisplayName("Parse valid discount card number")
    void testParseValidDiscountCardNumber() {
        String arg = "discountCard=1111";
        parser.parse(arg, context);
        assertEquals(new CardNumber(1111), context.getCardNumber().orElse(null));
    }

    @Test
    @DisplayName("Do not parse invalid discount card number")
    void testDoNotParseInvalidDiscountCardNumber() {
        String arg = "discountCard=abc1234";
        parser.parse(arg, context);
        assertTrue(context.getCardNumber().isEmpty());
    }

    @Test
    @DisplayName("Do not parse when no static prefix")
    void testDoNotParseWithoutStaticPrefix() {
        String arg = "card=1111";
        parser.parse(arg, context);
        assertTrue(context.getCardNumber().isEmpty());
    }

    @Test
    @DisplayName("Do not parse when card number is missing")
    void testDoNotParseWithMissingCardNumber() {
        String arg = "discountCard=";
        parser.parse(arg, context);
        assertTrue(context.getCardNumber().isEmpty());
    }
}
