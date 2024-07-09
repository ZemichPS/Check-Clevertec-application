package ru.clevertec.check.domain.model.exception.shared;

public abstract class AbstractException extends RuntimeException implements ErrorText{
    public AbstractException(String message) {
        super(message);
    }


}
