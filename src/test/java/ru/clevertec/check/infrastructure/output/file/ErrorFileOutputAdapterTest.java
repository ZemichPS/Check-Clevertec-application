package ru.clevertec.check.infrastructure.output.file;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.check.infrastructure.output.file.mapper.SimpleCSVStructureMapper;
import ru.clevertec.check.infrastructure.utils.CheckToCSVFileWriter;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ErrorFileOutputAdapterTest {


    @Spy
    CheckToCSVFileWriter CheckToCSVFileWriter = new CheckToCSVFileWriter(new SimpleCSVStructureMapper());

    @InjectMocks
    ErrorFileOutputAdapter errorFileOutputAdapter;

    @ParameterizedTest
    @ValueSource(strings = {"Error message 1", "Error message 2", "Error message 3"})
    void writeError(String errorMessage) throws IOException, URISyntaxException, InvocationTargetException, IllegalAccessException {
        String error = "Error";
        errorFileOutputAdapter.writeError(error);
    }
}