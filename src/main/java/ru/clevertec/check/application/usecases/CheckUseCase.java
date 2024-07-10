package ru.clevertec.check.application.usecases;

import ru.clevertec.check.domain.model.entity.Check;
import ru.clevertec.check.domain.model.valueobject.CardNumber;
import ru.clevertec.check.domain.model.valueobject.ProductId;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.Map;

public interface CheckUseCase {
    Check create(Map<ProductId, Integer> orderMap,
                 CardNumber cardNumber,
                 BigDecimal debitCardBalance);

    Check create(Map<ProductId, Integer> orderMap,
                 BigDecimal debitCardBalance);


}
