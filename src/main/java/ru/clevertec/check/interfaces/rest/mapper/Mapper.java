package ru.clevertec.check.interfaces.rest.mapper;

import ru.clevertec.check.domain.model.entity.Check;
import ru.clevertec.check.interfaces.rest.response.CheckItemDto;
import ru.clevertec.check.interfaces.rest.response.CheckResponseDto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Mapper {
    public static CheckResponseDto map(Check check){

        List<CheckItemDto> items = check.getItems().stream().map(
                checkItem -> CheckItemDto.builder()
                        .quantity(checkItem.qty())
                        .price(checkItem.price())
                        .discount(checkItem.discount())
                        .total(checkItem.total())
                        .build()
        ).toList();

        return CheckResponseDto.builder()
                .uuid(check.getCheckID().uuid())
                .creationDate(LocalDate.from(check.getCreationDate()))
                .creationTime(LocalTime.from(LocalDate.from(check.getCreationTime())))
                .checkItems(items)
                .build();
    }
}
