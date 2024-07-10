package ru.clevertec.check.infrastructure.output.file;

import ru.clevertec.check.application.ports.output.CheckFileOutputPort;

import java.nio.file.Path;

public abstract class AbstractFileOutputPort  implements CheckFileOutputPort {
    private final Path PATH_TO_RESULT_FILE;

    public AbstractFileOutputPort(Path pathToResultFile) {
        PATH_TO_RESULT_FILE = pathToResultFile;
    }

    public Path getPathToResultFile() {
        return PATH_TO_RESULT_FILE;
    }

    public String getDefaultResultName(){
        return "result.csv";
    }
}
