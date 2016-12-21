package com.progressoft.where.clause.builder;


public class NumericValue implements FormatableValue {

    private final Number number;

    public NumericValue(Number number) {
        this.number=number;
    }

    @Override
    public String formatted() {
        return number+"";
    }
}
