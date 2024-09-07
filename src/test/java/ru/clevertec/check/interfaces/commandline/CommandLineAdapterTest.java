package ru.clevertec.check.interfaces.commandline;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.clevertec.check.application.usecases.CheckUseCase;
import ru.clevertec.check.domain.model.entity.Check;
import ru.clevertec.check.domain.model.valueobject.CardNumber;
import ru.clevertec.check.domain.model.valueobject.ProductId;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CommandLineAdapterTest {
//
//    @Mock
//    private CheckUseCase checkUseCase;
//
//    private CommandLineAdapter commandLineAdapter;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        commandLineAdapter = new CommandLineAdapter(checkUseCase);
//    }
//
//    @Test
//    @DisplayName("Create check with card number")
//    void testCreateCheckWithCardNumber() {
//        String[] args = {"balanceDebitCard=50.00", "discountCard=12345", "101-2"};
//        Map<ProductId, Integer> productIdQuantityMap = new HashMap<>();
//        productIdQuantityMap.put(new ProductId(101), 2);
//        CardNumber cardNumber = new CardNumber(1111);
//        BigDecimal balance = new BigDecimal("50.00");
//
//        when(checkUseCase.create(any(Map.class), any(CardNumber.class), any(BigDecimal.class)))
//                .thenReturn(mock(Check.class));
//
//        Check result = commandLineAdapter.createCheck(args);
//
//        assertNotNull(result);
//        verify(checkUseCase, times(1))
//                .create(eq(productIdQuantityMap), eq(cardNumber), eq(balance));
//    }
//
//    @Test
//    @DisplayName("Create check without card number")
//    void testCreateCheckWithoutCardNumber() {
//        String[] args = {"balanceDebitCard=30.00", "202-5"};
//        Map<ProductId, Integer> productIdQuantityMap = new HashMap<>();
//        productIdQuantityMap.put(new ProductId(202), 5);
//        BigDecimal balance = new BigDecimal("30.00");
//
//        when(checkUseCase.create(any(Map.class), any(BigDecimal.class)))
//                .thenReturn(mock(Check.class));
//
//        Check result = commandLineAdapter.createCheck(args);
//
//        assertNotNull(result);
//        verify(checkUseCase, times(1))
//                .create(eq(productIdQuantityMap), eq(balance));
//    }
}
