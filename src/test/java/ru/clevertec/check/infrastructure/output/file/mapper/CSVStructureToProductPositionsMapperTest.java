package ru.clevertec.check.infrastructure.output.file.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.clevertec.check.domain.model.entity.RealDiscountCard;
import ru.clevertec.check.domain.model.valueobject.ProductPosition;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class CSVStructureToProductPositionsMapperTest {

    @ParameterizedTest
    @DisplayName("Should map list of string arrays to list of ProductPosition object")
    @MethodSource("getData")
    void map(List<String[]> source) {
        CSVStructureToProductPositionsMapper mapper = new CSVStructureToProductPositionsMapper();
        List<ProductPosition> productPositions = mapper.map(source);

        Assertions.assertAll(
                ()-> assertNotNull(productPositions),
                ()-> assertEquals(productPositions.size(), source.size()),
                ()-> assertInstanceOf(ProductPosition.class, productPositions.getFirst())
        );
    }

    static Stream<Arguments> getData() {
        return Stream.of(
                Arguments.of(
                        List.of(
                                new String[]{"1","banana", "17,10", "5", "+"},
                                new String[]{"1","cacao", "17,10", "5", "+"},
                                new String[]{"1","coca-cola", "17,10", "5", "+"},
                                new String[]{"1","button", "17,10", "5", "+"}
                        )
                )
        );
    }
}