package ru.clevertec.check;


import ru.clevertec.check.application.ports.intput.CheckInputPort;
import ru.clevertec.check.application.ports.output.*;
import ru.clevertec.check.application.usecases.CheckUseCase;
import ru.clevertec.check.infrastructure.output.file.CheckFileOutPutAdapter;
import ru.clevertec.check.infrastructure.output.file.DiscountCardFileOutPutAdapter;
import ru.clevertec.check.infrastructure.output.file.ErrorFileOutputAdapter;
import ru.clevertec.check.infrastructure.output.file.ProductFileOutputAdapter;
import ru.clevertec.check.infrastructure.output.file.mapper.CSVStructureToProductPositionsMapper;
import ru.clevertec.check.infrastructure.output.file.mapper.SimpleCSVStructureMapper;
import ru.clevertec.check.infrastructure.output.std.StdOutputAdapter;
import ru.clevertec.check.infrastructure.utils.ErrorToCSVFileWriter;
import ru.clevertec.check.infrastructure.utils.SimpleCVSFileReader;
import ru.clevertec.check.interfaces.commandline.CommandLineAdapter;

public class CheckRunner {
    public static void main(String[] args) {
        CheckOutputPort checkOutputPort = new CheckFileOutPutAdapter();
        DiscountCardOutputPort discountCardOutputPort = new DiscountCardFileOutPutAdapter(new SimpleCVSFileReader());
        ErrorOutputPort errorOutputPort = new ErrorFileOutputAdapter();
        ProductOutputPort productOutputPort = new ProductFileOutputAdapter(new SimpleCVSFileReader(), new CSVStructureToProductPositionsMapper());
        StdOutputPort stdOutputPort = new StdOutputAdapter();

        CheckUseCase createCheckUseCase = new CheckInputPort(checkOutputPort,
                productOutputPort,
                discountCardOutputPort,
                errorOutputPort,
                stdOutputPort
        );

        CommandLineAdapter commandLineAdapter = new CommandLineAdapter(createCheckUseCase);
        commandLineAdapter.createCheck(args);
    }
}
