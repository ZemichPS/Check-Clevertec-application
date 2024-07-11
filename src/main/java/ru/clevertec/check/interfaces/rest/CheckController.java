package ru.clevertec.check.interfaces.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.check.CheckRunner;
import ru.clevertec.check.application.usecases.CheckUseCase;
import ru.clevertec.check.domain.model.entity.Check;
import ru.clevertec.check.domain.model.valueobject.CardNumber;
import ru.clevertec.check.domain.model.valueobject.ProductId;
import ru.clevertec.check.interfaces.rest.mapper.Mapper;
import ru.clevertec.check.interfaces.rest.request.CreateNewCheckRequestDto;
import ru.clevertec.check.interfaces.rest.request.ProductPositionDto;
import ru.clevertec.check.interfaces.rest.response.CheckResponseDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/check")
public class CheckController {
    private final CheckUseCase checkUseCase;

    public CheckController(CheckUseCase checkUseCase) {
        this.checkUseCase = checkUseCase;
    }

    @PostMapping
    ResponseEntity<CheckResponseDto> create(@RequestBody CreateNewCheckRequestDto newCheckRequest) {
        Map<ProductId, Integer> orderMap = newCheckRequest.getPositions()
                .stream().collect(Collectors.toMap(item -> new ProductId(item.getId()), ProductPositionDto::getQuantity, Integer::sum));
        CardNumber cardNumber = new CardNumber(newCheckRequest.getDiscountCardNumber());
        BigDecimal debitCardBalance = newCheckRequest.getDebitCardBalance();

        Check newCheck = null;
        if (Objects.nonNull(debitCardBalance)) {
            newCheck = checkUseCase.create(orderMap, cardNumber, debitCardBalance);
        }
        newCheck = checkUseCase.create(orderMap, debitCardBalance);
        CheckResponseDto responseDto = Mapper.map(newCheck);
        return ResponseEntity.ok(responseDto);
    }
}
