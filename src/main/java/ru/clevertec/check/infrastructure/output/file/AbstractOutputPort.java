package ru.clevertec.check.infrastructure.output.file;

import ru.clevertec.check.application.ports.output.CheckOutputPort;

import java.nio.file.Path;

public abstract class AbstractOutputPort implements CheckOutputPort {
    private final Path PATH_TO_RESULT_FILE;

    public AbstractOutputPort(Path pathToResultFile) {
        PATH_TO_RESULT_FILE = pathToResultFile;
    }

    public Path getPathToResultFile() {
        return PATH_TO_RESULT_FILE;
    }

    public String getDefaultResultName(){
        return "result.csv";
    }
}
