package ru.clevertec.check.infrastructure.output.file;

import ru.clevertec.check.CheckRunner;
import ru.clevertec.check.application.ports.output.ErrorOutputPort;
import ru.clevertec.check.infrastructure.output.file.mapper.SimpleCSVStructureMapper;
import ru.clevertec.check.infrastructure.utils.CSVWriter;
import ru.clevertec.check.infrastructure.utils.ErrorToCSVFileWriter;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ErrorFileOutputAdapter implements ErrorOutputPort {

    private final Path DEFAULT_RESULT_FILE_PATH;
    private final CSVWriter<String> CSVWriter = new ErrorToCSVFileWriter(new SimpleCSVStructureMapper());

    public ErrorFileOutputAdapter(Path defaultResultFilePath) {
        DEFAULT_RESULT_FILE_PATH = defaultResultFilePath;
    }

    @Override
    public void writeError(String errorDescription) throws IOException, URISyntaxException, InvocationTargetException, IllegalAccessException {
        Path jarPath = Paths.get(CheckRunner.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParent();
        Path pathToResultFile = jarPath.resolve("result.csv");
        CSVWriter.write(errorDescription, DEFAULT_RESULT_FILE_PATH);

    }
}
