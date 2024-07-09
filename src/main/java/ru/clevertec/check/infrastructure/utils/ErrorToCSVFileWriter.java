package ru.clevertec.check.infrastructure.utils;

import ru.clevertec.check.infrastructure.output.file.mapper.shared.CSVStructureMapper;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

public class ErrorToCSVFileWriter implements CSVWriter<String> {

    private final CSVStructureMapper csvStructureMapper;

    public ErrorToCSVFileWriter(CSVStructureMapper csvStructureMapper) {
        this.csvStructureMapper = csvStructureMapper;
    }

    @Override
    public void write(String source, Path toFilePath) throws IOException {

        String result = new StringBuilder()
                .append("ERROR" + "\n")
                .append(csvStructureMapper.getLineFromValues(CSVStructureMapper.RegisterType.UPPER_CASE, source))
                .toString();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(toFilePath.toFile(), true))) {
            writer.write(result);
            writer.flush();
        }
    }
}
