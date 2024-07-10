package ru.clevertec.check.interfaces.commandline;

import ru.clevertec.check.application.usecases.CheckUseCase;
import ru.clevertec.check.domain.model.entity.Check;
import ru.clevertec.check.domain.model.valueobject.CardNumber;
import ru.clevertec.check.domain.model.valueobject.ProductId;
import ru.clevertec.check.interfaces.commandline.parser.*;

import java.math.BigDecimal;
import java.util.Map;

public class CommandLineAdapter {
    private final CheckUseCase createCheckUseCase;
    private final ParserContext parserContext = new ParserContext();

    public CommandLineAdapter(CheckUseCase createCheckUseCase) {
        this.createCheckUseCase = createCheckUseCase;
        parserContext.addParser(new BalanceDebitCardRegexParser());
        parserContext.addParser(new IdQuantityToMapRegexParser());
        parserContext.addParser(new DiscountCardNumberParser());
        parserContext.addParser(new PathFromProductFileRegexParser());
        parserContext.addParser(new PathToSaveFileRegexParser());
    }

    public Check createCheck(String... args){
        ArgumentParsingContext argumentParsingContext = parserContext.parseArguments(args);
        Map<ProductId, Integer> productIdQuantityMap = argumentParsingContext.getProductIdQuantityMap();
        BigDecimal debitCardBalance =  argumentParsingContext.getBalanceDebitCard().orElse(BigDecimal.ZERO);

        if(argumentParsingContext.getCardNumber().isPresent()){
            CardNumber cardNumber = argumentParsingContext.getCardNumber().get();
            return createCheckUseCase.create(productIdQuantityMap, cardNumber, debitCardBalance);
        }
        return createCheckUseCase.create(productIdQuantityMap, debitCardBalance);
    }

}