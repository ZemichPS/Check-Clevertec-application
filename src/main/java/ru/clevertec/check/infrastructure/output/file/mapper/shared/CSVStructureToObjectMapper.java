package ru.clevertec.check.infrastructure.output.file.mapper.shared;

import java.util.List;

public interface CSVStructureToObjectMapper<T> {

    List<T> map(List<String[]> source);
}
