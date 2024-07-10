package ru.clevertec.check.interfaces.commandline;

import ru.clevertec.check.application.usecases.CheckUseCase;
import ru.clevertec.check.domain.model.entity.Check;
import ru.clevertec.check.domain.model.valueobject.CardNumber;
import ru.clevertec.check.domain.model.valueobject.ProductId;
import ru.clevertec.check.interfaces.commandline.parser.*;

import java.math.BigDecimal;
import java.util.Map;

public class CommandLineInputAdapter {
    private final CheckUseCase createCheckUseCase;

    public CommandLineInputAdapter(CheckUseCase createCheckUseCase) {
        this.createCheckUseCase = createCheckUseCase;
    }

    Check create(Map<ProductId, Integer> orderMap,
                 CardNumber cardNumber,
                 BigDecimal debitCardBalance) {

        return createCheckUseCase.create(orderMap, cardNumber, debitCardBalance);
    }

    Check create(Map<ProductId, Integer> orderMap,
                 BigDecimal debitCardBalance) {
        return createCheckUseCase.create(orderMap, debitCardBalance);
    }

}
