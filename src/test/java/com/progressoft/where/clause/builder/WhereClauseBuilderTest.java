package com.progressoft.where.clause.builder;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class WhereClauseBuilderTest {

    public static final String FIRST_PROPERTY = "property1";
    public static final String FIRST_VALUE = "value1";
    public static final String SECOND_PROPERTY = "property2";
    public static final String SECOND_VALUE = "value2";
    public static final String SOME_VALUE = "anyValue";
    private WhereClauseBuilder whereClauseBuilder;

    @Before
    public void setUp() throws Exception {
        whereClauseBuilder = new WhereClauseBuilder();
    }

    private WhereClauseBuilder.LogicalWhereClauseBuilder createWhereClauseBuilderWithEqualsFilter(String property, String value1) {
        return new WhereClauseBuilder().filter(Filter.equalsFilter(property, new StringValue(value1)));
    }

    private WhereClauseBuilder.LogicalWhereClauseBuilder createWhereClauseBuilderWithEqualsFilter(String property, int value1) {
        return new WhereClauseBuilder().filter(Filter.equalsFilter(property, new NumericValue(value1)));
    }

    private WhereClauseBuilder.LogicalWhereClauseBuilder createWhereClauseBuilderWithNotEqualsFilter(String property, String value1) {
        return new WhereClauseBuilder().filter(Filter.notEqualsFilter(property, new StringValue(value1)));
    }

    @Test
    public void givenNewlyInitializedBuilder_WhenCallingBuild_ThenEmptyStringGenerated() throws Exception {
        assertEquals("", whereClauseBuilder.build());
    }

    @Test
    public void givenWhereClauseBuilder_WhenAddingFilter_AndCallingBuild_ReturnExpectedResult() throws Exception {
        assertEquals("property1='value1'",
                createWhereClauseBuilderWithEqualsFilter(FIRST_PROPERTY, FIRST_VALUE).build());
        assertEquals("property2='value2'", createWhereClauseBuilderWithEqualsFilter("property2", "value2").build());
    }

    @Test(expected = BaseFilter.InvalidPropertyException.class)
    public void givenWhereClauseBuilder_WhenAddingFilterWithNullProperty_ThenThrowException()
            throws Exception {
        createWhereClauseBuilderWithEqualsFilter(null, SOME_VALUE);
    }

    @Test(expected = BaseFilter.InvalidPropertyException.class)
    public void givenwhereClauseBuilder_WhenAddingFilterWithEmptyProperty_ThenThrowException() throws Exception {
        createWhereClauseBuilderWithEqualsFilter("", SOME_VALUE);
    }

    @Test(expected = BaseFilter.InvalidPropertyException.class)
    public void givenWhereClauseBuilder_WhenAddingFilterWithSpacesProperty_ThenThrowException() throws Exception {
        createWhereClauseBuilderWithEqualsFilter(" ", SOME_VALUE);
    }

    @Test
    public void givenWhereClauseBuilder_WhenPassingNullValueToTheFilter_ThenReturnsEqualsFilterForEmptyValue()
            throws Exception {
        assertEquals("property1=''", createWhereClauseBuilderWithEqualsFilter(FIRST_PROPERTY, null).build());
    }

    @Test(expected = Filter.InvalidValueException.class)
    public void givenWhereClauseBuilder_whenPassingNullFormattableToEqualityFilterValue_thenShouldThrowException()
            throws Exception {
        Filter.equalsFilter(FIRST_PROPERTY, null);
    }

    @Test
    public void givenWhereClauseBuilder_WhenAddingEqualFilterWithIntegerValue_ThenShouldReturnEqualsFilterForIntegter()
            throws Exception {
        assertEquals("property1=2", createWhereClauseBuilderWithEqualsFilter(FIRST_PROPERTY, 2).build());
    }

    @Test
    public void givenWhereClauseBuilder_WhenAddingNotEqualFilter_ThenShouldNotEqualsWhereClause()
            throws Exception {
        assertEquals("property1!='value1'",
                createWhereClauseBuilderWithNotEqualsFilter(FIRST_PROPERTY, FIRST_VALUE).build());
    }

    @Test
    public void givenWhereClauseBuilder_WhenAddingIsNullFilterWithProperty_ThenShouldReturnIsNullFilter() {
        assertEquals("property1 is null",
                new WhereClauseBuilder().filter(Filter.isNullFilter(FIRST_PROPERTY)).build());
    }

    @Test
    public void givenWhereClauseBuilder_whenAddingNotNullFilter_thenShouldGenerateNotNullWhereClause()
            throws Exception {
        assertEquals("property1 is not null",
                new WhereClauseBuilder().filter(Filter.isNotNullFilter(FIRST_PROPERTY)).build());
    }

    @Test
    public void givenWhereClauseBuilder_whenAddingGreaterThanFilter_thenShouldGenerateGreaterThanWhereClause()
            throws Exception {
        assertEquals("property1 >1",
                new WhereClauseBuilder().filter(Filter.greaterThanFilter(FIRST_PROPERTY, new NumericValue(1)))
                        .build());
    }

    @Test
    public void givenWhereClauseBuilder_whenAddingLessThanFilter_thenShouldGenerateLessThanWhereClause()
            throws Exception {
        assertEquals("property1 <1",
                new WhereClauseBuilder().filter(Filter.lessThanFilter(FIRST_PROPERTY, new NumericValue(1))).build());
    }

    @Test(expected = Filter.InvalidValueException.class)
    public void creatingGreaterThanFilterWithNullFormattableValue_thenShouldThrowException() throws Exception {
        Filter.greaterThanFilter(FIRST_PROPERTY, null);
    }

    @Test
    public void givenWhereClause_whenAndingTwoFilters_thenShouldGenerateWhereClauseWithAndBetweenTheTwoFilters()
            throws Exception {
        assertEquals("property1='value1' AND property2='value2'",
                createWhereClauseBuilderWithEqualsFilter(FIRST_PROPERTY, FIRST_VALUE)
                        .and(Filter.equalsFilter(SECOND_PROPERTY, new StringValue(SECOND_VALUE))).build());
    }

    @Test(expected = WhereClauseBuilder.InvalidFilterException.class)
    public void givenWhereClause_addingNullFilter_thenShouldThrowException() throws Exception {
        new WhereClauseBuilder().filter(null);
    }

    @Test(expected = WhereClauseBuilder.InvalidFilterException.class)
    public void givenWhereClause_andingNullFilter_thenShouldThrowException() throws Exception {
        new WhereClauseBuilder().filter(Filter.equalsFilter(FIRST_PROPERTY, new StringValue(FIRST_VALUE))).and(null);
    }

    @Test
    public void givenWhereClause_whenOringTwoFilters_thenShouldGenerateWhereClauseWithOrBetweenTheTwoFilters()
            throws Exception {
        assertEquals("property1='value1' OR property2='value2'",
                createWhereClauseBuilderWithEqualsFilter(FIRST_PROPERTY, FIRST_VALUE)
                        .or(Filter.equalsFilter(SECOND_PROPERTY, new StringValue(SECOND_VALUE))).build());
    }

    @Test(expected = WhereClauseBuilder.InvalidFilterException.class)
    public void givenWhereClause_oringNullFilter_thenShouldThrowException() throws Exception {
        new WhereClauseBuilder().filter(Filter.equalsFilter(FIRST_PROPERTY, new StringValue(FIRST_VALUE))).or(null);
    }

}
