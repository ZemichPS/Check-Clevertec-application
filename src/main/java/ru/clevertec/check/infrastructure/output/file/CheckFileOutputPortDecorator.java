package ru.clevertec.check.infrastructure.output.file;

import ru.clevertec.check.application.ports.output.CheckFileOutputPort;
import ru.clevertec.check.domain.model.entity.Check;
import ru.clevertec.check.domain.model.exception.BadFilePathException;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.nio.file.Path;

public class CheckFileOutputPortDecorator extends AbstractFileOutputPort {

    private final CheckFileOutputPort decoratedCheckFileOutputPort;

    public CheckFileOutputPortDecorator(CheckFileOutputPort decoratedCheckFileOutputPort,
                                        Path pathToResultFile) {
        super(pathToResultFile);
        this.decoratedCheckFileOutputPort = decoratedCheckFileOutputPort;
    }

    @Override
    public Check persist(Check check) throws InvocationTargetException, IllegalAccessException, IOException, URISyntaxException, BadFilePathException {

        if (getDefaultResultName().equals(getPathToResultFile().toString())) {
            throw new BadFilePathException("The file for recording the result was not passed. The result will be written to the resource file: %s."
                    .formatted(getPathToResultFile()));
        }
        decoratedCheckFileOutputPort.persist(check);
        return check;
    }
}

