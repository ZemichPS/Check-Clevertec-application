package ru.clevertec.check.infrastructure.output.file;

import ru.clevertec.check.CheckRunner;
import ru.clevertec.check.application.ports.output.CheckFileOutputPort;
import ru.clevertec.check.domain.model.entity.Check;
import ru.clevertec.check.domain.model.exception.BadFilePathException;
import ru.clevertec.check.infrastructure.output.file.mapper.SimpleCSVStructureMapper;
import ru.clevertec.check.infrastructure.utils.CSVWriter;
import ru.clevertec.check.infrastructure.utils.CheckToCSVFileWriter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.Objects;
import java.util.stream.Collectors;

public class CheckFileFileOutPutAdapter implements CheckFileOutputPort {

    private final CSVWriter<Check> CSVWriter = new CheckToCSVFileWriter(new SimpleCSVStructureMapper());
    private final Path PATH_TO_RESULT_FILE;

    public CheckFileFileOutPutAdapter(Path defaulResultPath) {
        this.PATH_TO_RESULT_FILE = defaulResultPath;
    }

    @Override
    public Check persist(Check check) throws IOException, URISyntaxException, InvocationTargetException, IllegalAccessException {
        ClassLoader classLoader = CheckRunner.class.getClassLoader();
        String resultPath = PATH_TO_RESULT_FILE.toString();

        if (Objects.nonNull(classLoader.getResource(resultPath))) {
            try (InputStream inputStream = classLoader.getResourceAsStream(resultPath)) {
                if (inputStream == null) {
                    throw new BadFilePathException("Resource not found: discountCards.csv");
                }
                return new BufferedReader(new InputStreamReader(inputStream))
                        .lines().collect(Collectors.toList());
            }


        CSVWriter.write(check, PATH_TO_RESULT_FILE);
        return check;
    }

}



