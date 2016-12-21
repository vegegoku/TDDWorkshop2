package com.progressoft.where.clause.builder;

import java.util.Objects;

public class StringValue implements FormatableValue {

    public static final String QOUTE = "'";
    private final String value;

    public StringValue(String value) {
        this.value = value;
    }

    @Override
    public String formatted() {
        return QOUTE + (Objects.isNull(value) ? "" : value) + QOUTE;
    }
}
