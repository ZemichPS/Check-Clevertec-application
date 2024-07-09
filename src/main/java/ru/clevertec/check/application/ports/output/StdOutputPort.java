package ru.clevertec.check.application.ports.output;

import ru.clevertec.check.domain.model.entity.Check;

public interface StdOutputPort {
    void printCheck(Check check);

    void printError(String errorText);
}
