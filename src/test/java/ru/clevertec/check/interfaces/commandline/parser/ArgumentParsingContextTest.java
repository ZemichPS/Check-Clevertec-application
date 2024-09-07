package ru.clevertec.check.interfaces.commandline.parser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.clevertec.check.domain.model.valueobject.CardNumber;
import ru.clevertec.check.domain.model.valueobject.ProductId;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ArgumentParsingContextTest {

    private ArgumentParsingContext context;
    private CardNumber cardNumber;
    private BigDecimal balance;
    private Map<ProductId, Integer> productIdQuantityMap;

    @BeforeEach
    void setUp() {
        cardNumber = new CardNumber(1111);
        balance = BigDecimal.valueOf(100.0);
        productIdQuantityMap = new HashMap<>();
        productIdQuantityMap.put(new ProductId(1), 2);
        context = new ArgumentParsingContext(cardNumber, balance, productIdQuantityMap);
    }

    @Test
    @DisplayName("Get card number when present")
    void testGetCardNumberPresent() {
        Optional<CardNumber> result = context.getCardNumber();
        assertTrue(result.isPresent());
        assertEquals(cardNumber, result.get());
    }

    @Test
    @DisplayName("Get card number when not present")
    void testGetCardNumberNotPresent() {
        context = new ArgumentParsingContext();
        assertTrue(context.getCardNumber().isEmpty());
    }

    @Test
    @DisplayName("Set and get card number")
    void testSetCardNumber() {
        CardNumber newCard = new CardNumber(1111);
        context.setCardNumber(newCard);
        assertEquals(newCard, context.getCardNumber().orElse(null));
    }

    @Test
    @DisplayName("Get balance when present")
    void testGetBalanceDebitCardPresent() {
        Optional<BigDecimal> result = context.getBalanceDebitCard();
        assertTrue(result.isPresent());
        assertEquals(balance, result.get());
    }

    @Test
    @DisplayName("Get balance when not present")
    void testGetBalanceDebitCardNotPresent() {
        context = new ArgumentParsingContext();
        assertTrue(context.getBalanceDebitCard().isEmpty());
    }

    @Test
    @DisplayName("Set and get balance debit card")
    void testSetBalanceDebitCard() {
        BigDecimal newBalance = BigDecimal.valueOf(200.0);
        context.setBalanceDebitCard(newBalance);
        assertEquals(newBalance, context.getBalanceDebitCard().orElse(null));
    }

    @Test
    @DisplayName("Get product ID and quantity map")
    void testGetProductIdQuantityMap() {
        Map<ProductId, Integer> result = context.getProductIdQuantityMap();
        assertEquals(productIdQuantityMap, result);
    }

    @Test
    @DisplayName("Add product ID and quantity")
    void testAddProductIdAndQuantity() {
        ProductId productId = new ProductId(2);
        context.addProductIdAndQuantity(productId, 3);
        assertEquals(3, context.getProductIdQuantityMap().get(productId));

        context.addProductIdAndQuantity(productId, 2);
        assertEquals(5, context.getProductIdQuantityMap().get(productId));
    }

    @Test
    @DisplayName("ToString method")
    void testToString() {
        String expected = "ArgumentParsingContext{" +
                "cardNumber=" + cardNumber +
                ", balanceDebitCard=" + balance +
                ", productIdQuantityEntryList=" + productIdQuantityMap +
                '}';
        assertEquals(expected, context.toString());
    }
}
