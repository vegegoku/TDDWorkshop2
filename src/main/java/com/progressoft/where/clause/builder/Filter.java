package com.progressoft.where.clause.builder;

import java.util.Objects;

public class Filter extends BaseFilter {

    private static final String EQUALS = "=";
    private static final String NOT_EQUALS = "!=";
    private static final String GREATER_THAN=" >";
    private static final String LESS_THAN=" <";
    private static final String IS_NULL =" is null";
    private static final String IS_NOT_NULL =" is not null";
    private static final FormatableValue EMPTY_VALUE=() -> "";

    private final FormatableValue value;

    private Filter(String property, String operator, FormatableValue value) {
        super(property, operator);
        if(Objects.isNull(value))
            throw new InvalidValueException();
        this.value = value;
    }

    @Override
    protected String appendedValue() {
        return this.value.formatted();
    }

    public static BaseFilter equalsFilter(String property, FormatableValue value){
        return new Filter(property, EQUALS, value);
    }

    public static BaseFilter notEqualsFilter(String property, FormatableValue value){
        return new Filter(property, NOT_EQUALS, value);
    }

    public static BaseFilter greaterThanFilter(String property, NumericValue value){
        return new Filter(property, GREATER_THAN, value);
    }

    public static BaseFilter lessThanFilter(String property, NumericValue value) {
        return new Filter(property, LESS_THAN, value);
    }

    public static BaseFilter isNullFilter(String property){
        return new Filter(property, IS_NULL, EMPTY_VALUE);
    }

    public static BaseFilter isNotNullFilter(String property){
        return new Filter(property, IS_NOT_NULL, EMPTY_VALUE);
    }

    public class InvalidValueException extends RuntimeException{
    }
}
