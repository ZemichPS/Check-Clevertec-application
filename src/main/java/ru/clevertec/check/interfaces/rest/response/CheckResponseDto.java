package ru.clevertec.check.interfaces.rest.response;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CheckResponseDto {
    private UUID uuid;
    private List<CheckItemDto> checkItems;
    private LocalDate creationDate;
    private LocalTime creationTime;
    private DiscountCardDto discountCard;
}
