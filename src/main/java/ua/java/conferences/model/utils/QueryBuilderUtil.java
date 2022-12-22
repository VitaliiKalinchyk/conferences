package ua.java.conferences.model.utils;

import ua.java.conferences.model.utils.query.*;

public class QueryBuilderUtil {
    public static QueryBuilder userQueryBuilder() {
        return new UserQueryBuilder();
    }

    public static QueryBuilder eventQueryBuilder() {
        return new EventQueryBuilder();
    }

    public static QueryBuilder visitorEventQueryBuilder() {
        return new VisitorEventQueryBuilder();
    }

    private QueryBuilderUtil() {}
}