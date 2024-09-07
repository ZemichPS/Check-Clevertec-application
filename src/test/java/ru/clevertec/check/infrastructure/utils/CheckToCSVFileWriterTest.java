package ru.clevertec.check.infrastructure.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.check.domain.model.entity.Check;
import ru.clevertec.check.infrastructure.output.file.mapper.SimpleCSVStructureMapper;
import testconfig.InjectCheck;
import testconfig.InjectCheckProcessor;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith({MockitoExtension.class, InjectCheckProcessor.class})
class CheckToCSVFileWriterTest {

    @Spy
    private SimpleCSVStructureMapper structureMapper;
    @InjectMocks
    private CheckToCSVFileWriter checkToCSVFileWriter;

    @InjectCheck
    private Check check;

    @Test
    void write() throws IOException, InvocationTargetException, IllegalAccessException {
        Path pathToFile = Path.of("E:/result.csv");
        checkToCSVFileWriter.write(check, Path.of("E:/result.csv"));
        System.out.println(Files.readAllLines(pathToFile));

        Assertions.assertAll(
                ()->  assertTrue(pathToFile.toFile().exists()),
                ()-> assertTrue(String.join(",", Files.readAllLines(pathToFile)).contains(check.getItems().getFirst().description()))
        );

    }
}