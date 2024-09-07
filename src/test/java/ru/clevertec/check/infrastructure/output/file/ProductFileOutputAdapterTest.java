package ru.clevertec.check.infrastructure.output.file;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.check.domain.model.entity.Product;
import ru.clevertec.check.domain.model.valueobject.*;
import ru.clevertec.check.infrastructure.output.file.ProductFileOutputAdapter;
import ru.clevertec.check.infrastructure.output.file.mapper.CSVStructureToProductPositionsMapper;
import ru.clevertec.check.infrastructure.utils.CSVReader;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductFileOutputAdapterTest {

    @Mock
    private CSVReader csvReaderMock;
    @Mock
    private CSVStructureToProductPositionsMapper mapperMock;
    @InjectMocks
    private ProductFileOutputAdapter productFileOutputAdapter;

    @Test
    @DisplayName("Test findAll success")
    void testFindAll() throws URISyntaxException, IOException {

        List<String[]> rawProductData = new ArrayList<>(
                Collections.singleton(new String[]{"1", "Packed potatoes 1kg", "1.47", "20", "+"}));

        Product product = new Product(new ProductId(1), SaleConditionType.WHOLESALE);
        product.addProductName(new ProductName("Milk 1l"));
        product.addProductPrice(new Price(new BigDecimal("1.12")));
        List<ProductPosition> productPositions = List.of(new ProductPosition(product, 10));


        when(csvReaderMock.readAndFilterRecords(anyString(), anyList())).thenReturn(rawProductData);
        when(mapperMock.map(rawProductData)).thenReturn(productPositions);

        List<ProductPosition> result = productFileOutputAdapter.findAll();

        assertEquals(1, result.size());
        assertEquals(productPositions, result);
        verify(csvReaderMock, times(1)).readAndFilterRecords(anyString(), anyList());
        verify(mapperMock, times(1)).map(rawProductData);
    }

    @Test
    @DisplayName("Test findAll throws IOException")
    void testFindAllThrowsIOException() throws URISyntaxException, IOException {
        when(csvReaderMock.readAndFilterRecords(anyString(), anyList())).thenThrow(IOException.class);

        assertThrows(IOException.class, () -> productFileOutputAdapter.findAll());
    }

}
