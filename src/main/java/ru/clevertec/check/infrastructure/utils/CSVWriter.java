package ru.clevertec.check.infrastructure.utils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.nio.file.Path;

public interface CSVWriter<T> {
    void write(T source, Path toFilePath) throws InvocationTargetException, IllegalAccessException, IOException, URISyntaxException;
}
