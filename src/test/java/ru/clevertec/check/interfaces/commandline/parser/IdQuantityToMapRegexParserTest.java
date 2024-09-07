package ru.clevertec.check.interfaces.commandline.parser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.clevertec.check.domain.model.valueobject.ProductId;

import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class IdQuantityToMapRegexParserTest {

    private IdQuantityToMapRegexParser parser;
    private ArgumentParsingContext context;

    @BeforeEach
    void setUp() {
        parser = new IdQuantityToMapRegexParser();
        context = new ArgumentParsingContext();
    }

    @Test
    @DisplayName("Parse valid product id and quantity")
    void testParseValidIdQuantity() {
        String arg = "123-5";
        parser.parse(arg, context);
        Map<ProductId, Integer> productIdQuantityMap = context.getProductIdQuantityMap();
        assertEquals(1, productIdQuantityMap.size());
        assertEquals(5, productIdQuantityMap.get(new ProductId(123)));
    }

    @Test
    @DisplayName("Do not parse invalid format")
    void testDoNotParseInvalidFormat() {
        String arg = "123-abc";
        parser.parse(arg, context);
        assertTrue(context.getProductIdQuantityMap().isEmpty());
    }

    @Test
    @DisplayName("Do not parse if no hyphen present")
    void testDoNotParseWithoutHyphen() {
        String arg = "1235";
        parser.parse(arg, context);
        assertTrue(context.getProductIdQuantityMap().isEmpty());
    }

    @Test
    @DisplayName("Parse multiple id-quantity pairs")
    void testParseMultipleIdQuantityPairs() {
        String arg = "201-5 445-3 451-4 4-2";
        parser.parse(arg, context);
        Map<ProductId, Integer> productIdQuantityMap = context.getProductIdQuantityMap();

        assertEquals(4, productIdQuantityMap.size());
        assertEquals(3, productIdQuantityMap.get(new ProductId(445)));
        assertEquals(2, productIdQuantityMap.get(new ProductId(4)));
    }
}
