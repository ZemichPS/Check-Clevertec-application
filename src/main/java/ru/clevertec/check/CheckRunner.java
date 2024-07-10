package ru.clevertec.check;


import ru.clevertec.check.application.ports.intput.CheckInputPort;
import ru.clevertec.check.application.ports.output.*;
import ru.clevertec.check.application.usecases.CheckUseCase;
import ru.clevertec.check.infrastructure.output.file.CheckFileOutPutAdapter;
import ru.clevertec.check.infrastructure.output.file.DiscountCardFileOutPutAdapter;
import ru.clevertec.check.infrastructure.output.file.ErrorFileOutputAdapter;
import ru.clevertec.check.infrastructure.output.file.ProductFileOutputAdapter;
import ru.clevertec.check.infrastructure.output.std.StdOutputAdapter;
import ru.clevertec.check.interfaces.commandline.CommandLineAdapter;
import ru.clevertec.check.interfaces.commandline.parser.ArgumentParsingContext;
import ru.clevertec.check.interfaces.commandline.parser.PathToFromFileRegexParser;

import java.util.Arrays;

public class CheckRunner {
    public static void main(String[] args) {
        String args2 = "1-2 discountCard=1111 balanceDebitCard=100 pathToFile=files/discountCards.csv saveToFile=files/result.csv";

        ArgumentParsingContext argumentParsingContext = new ArgumentParsingContext();
        PathToFromFileRegexParser parser = new PathToFromFileRegexParser();
        parser.parse(args2, argumentParsingContext);

        System.out.println(argumentParsingContext.getRelativePathToSave());
    }
}


// CheckOutputPort checkOutputPort = new CheckFileOutPutAdapter();
//        DiscountCardOutputPort discountCardOutputPort = new DiscountCardFileOutPutAdapter();
//        ErrorOutputPort errorOutputPort = new ErrorFileOutputAdapter();
//        ProductOutputPort productOutputPort = new ProductFileOutputAdapter();
//        StdOutputPort stdOutputPort = new StdOutputAdapter();
//
//        CheckUseCase createCheckUseCase = new CheckInputPort(checkOutputPort,
//                productOutputPort,
//                discountCardOutputPort,
//                errorOutputPort,
//                stdOutputPort
//        );
//
//        CommandLineAdapter commandLineAdapter = new CommandLineAdapter(createCheckUseCase);
//        commandLineAdapter.createCheck(args);
//    }
