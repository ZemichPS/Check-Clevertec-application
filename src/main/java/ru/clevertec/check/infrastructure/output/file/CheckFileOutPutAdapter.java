package ru.clevertec.check.infrastructure.output.file;

import ru.clevertec.check.CheckRunner;
import ru.clevertec.check.application.ports.output.CheckOutputPort;
import ru.clevertec.check.domain.model.entity.Check;
import ru.clevertec.check.infrastructure.output.file.mapper.SimpleCSVStructureMapper;
import ru.clevertec.check.infrastructure.utils.CSVWriter;
import ru.clevertec.check.infrastructure.utils.CheckToCSVFileWriter;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CheckFileOutPutAdapter implements CheckOutputPort {

    private final CSVWriter<Check> CSVWriter = new CheckToCSVFileWriter(new SimpleCSVStructureMapper());
    private final Path DEFAULT_RESULT_FILE_PATH = Paths.get("result.csv");


    @Override
    public Check persist(Check check) throws IOException, URISyntaxException, InvocationTargetException, IllegalAccessException {
        CSVWriter.write(check, DEFAULT_RESULT_FILE_PATH);
        return check;
    }
}



