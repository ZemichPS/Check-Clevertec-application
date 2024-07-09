package ru.clevertec.check.domain.specification.shared;

public abstract class AbstractSpecification<T> implements Specification<T>{
    public abstract void check(T item);
}
