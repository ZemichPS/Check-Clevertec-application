package ru.clevertec.check.infrastructure.output.file.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import ru.clevertec.check.domain.model.entity.RealDiscountCard;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class CSVStructureToDiscountCardsMapperTest {

    @ParameterizedTest
    @DisplayName("Should map list of string arrays to list of RealDiscountCard object")
    @MethodSource("getData")
    void map(List<String[]> source) {
        CSVStructureToDiscountCardsMapper mapper = new CSVStructureToDiscountCardsMapper();
        List<RealDiscountCard> discountCards = mapper.map(source);
        Assertions.assertAll(
                ()-> assertNotNull(discountCards),
                ()-> assertEquals(discountCards.size(), source.size()),
                ()-> assertInstanceOf(RealDiscountCard.class, discountCards.getFirst())
        );

    }

    static Stream<Arguments> getData() {
        return Stream.of(
                Arguments.of(
                        List.of(
                                new String[]{"1", "1111", "5"},
                                new String[]{"2", "2222", "4"},
                                new String[]{"3", "3333", "3"},
                                new String[]{"4", "4444", "2"}
                        )
                )
        );
    }
}