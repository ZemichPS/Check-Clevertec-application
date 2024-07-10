package ru.clevertec.check;


import ru.clevertec.check.application.ports.intput.CheckInputPort;
import ru.clevertec.check.application.ports.output.*;
import ru.clevertec.check.application.usecases.CheckUseCase;
import ru.clevertec.check.domain.model.valueobject.ProductId;
import ru.clevertec.check.infrastructure.output.file.*;
import ru.clevertec.check.infrastructure.output.std.StdOutputAdapter;
import ru.clevertec.check.interfaces.commandline.parser.*;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class CheckRunner {
    private final Path DEFAULT_RESULT_FILE_PATH = Paths.get("result.csv");
    private final Path DEFAULT_DISCOUNT_CARD_FILE_PATH = Paths.get("discountCards.csv");

    DiscountCardOutputPort discountCardOutputPort;
    StdOutputPort stdOutputPort;
    ProductFileOutputPort productOutputPort;
    CheckFileOutputPort checkFileOutputPort;
    CheckFileOutputPort decoratedCheckFileOutputPort;
    ErrorFileOutputPort errorFileOutputPort;
    CheckUseCase checkUseCase;

    public static void main(String[] args) {

        ParserContext parserContext = new ParserContext();
        parserContext.addParser(new BalanceDebitCardRegexParser());
        parserContext.addParser(new IdQuantityToMapRegexParser());
        parserContext.addParser(new DiscountCardNumberParser());
        parserContext.addParser(new PathFromProductFileRegexParser());
        parserContext.addParser(new PathToSaveFileRegexParser());
        ArgumentParsingContext argumentParsingContext = parserContext.parseArguments(args);

        new CheckRunner().setAdapter(argumentParsingContext);
    }

    void setAdapter(ArgumentParsingContext argumentParsingContext) {

        Map<ProductId, Integer> productIdQuantityMap = argumentParsingContext.getProductIdQuantityMap();
        BigDecimal debitCardBalance = argumentParsingContext.getBalanceDebitCard().orElse(BigDecimal.ZERO);
        Path productFilePath = argumentParsingContext.getPathFromProductFile().orElse(Path.of(""));
        Path resultFilePath = argumentParsingContext.getPathToSaveResultFile().orElse(DEFAULT_RESULT_FILE_PATH);

        stdOutputPort = new StdOutputAdapter();
        discountCardOutputPort = new DiscountCardFileOutPutAdapter(DEFAULT_DISCOUNT_CARD_FILE_PATH);
        errorFileOutputPort = new ErrorFileFileOutputAdapter(resultFilePath);
        productOutputPort = new ProductFileFileOutputAdapter(productFilePath);

        checkFileOutputPort = new CheckFileOutPutAdapter(resultFilePath);
        decoratedCheckFileOutputPort = new CheckFileOutputPortDecorator(checkFileOutputPort, resultFilePath);

        checkUseCase = new CheckInputPort(
                decoratedCheckFileOutputPort,
                productOutputPort,
                discountCardOutputPort,
                errorFileOutputPort,
                stdOutputPort
        );

        argumentParsingContext.getCardNumber().ifPresentOrElse(
                cardNumber -> {
                    checkUseCase.create(productIdQuantityMap, cardNumber, debitCardBalance);
                }, () -> checkUseCase.create(productIdQuantityMap, debitCardBalance)
        );
    }
}

