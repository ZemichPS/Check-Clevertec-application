package ru.clevertec.check.application.ports.output;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;

public interface ErrorFileOutputPort {
    void writeError(String errorText) throws IOException, InvocationTargetException, IllegalAccessException, URISyntaxException;
}
