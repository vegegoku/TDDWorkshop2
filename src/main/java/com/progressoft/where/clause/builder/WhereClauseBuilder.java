package com.progressoft.where.clause.builder;

import java.util.Objects;

public class WhereClauseBuilder {

    public LogicalWhereClauseBuilder filter(BaseFilter filter) {
        if(Objects.isNull(filter))
            throw new InvalidFilterException();
        return new LogicalWhereClauseBuilder(filter);
    }

    public String build() {
        return "";
    }

    class LogicalWhereClauseBuilder{
        private static final String AND = " AND ";
        public static final String OR = " OR ";

        private String whereClause = "";

        public LogicalWhereClauseBuilder(BaseFilter filter) {
            whereClause = filter.asFilterString();
        }

        public LogicalWhereClauseBuilder and(BaseFilter filter) {
            return logicalFilter(AND, filter);
        }

        public LogicalWhereClauseBuilder or(BaseFilter filter) {
            return logicalFilter(OR, filter);
        }

        private LogicalWhereClauseBuilder logicalFilter(String operator, BaseFilter filter){
            if(Objects.isNull(filter))
                throw new InvalidFilterException();
            whereClause+= operator +filter.asFilterString();
            return this;
        }

        public String build() {
            return whereClause;
        }

    }

    public class InvalidFilterException extends RuntimeException{
    }
}
