package com.progressoft.where.clause.builder;

import java.util.Objects;

public abstract class BaseFilter {

    private final String property;
    private final String operator;

    public BaseFilter(String property, String operator) {
        if (Objects.isNull(property) || property.trim().isEmpty())
            throw new InvalidPropertyException();
        this.property = property;
        this.operator = operator;
    }

    public String asFilterString(){
        return property+operator+appendedValue();
    }

    protected abstract String appendedValue();

    public class InvalidPropertyException extends RuntimeException {
    }
}
