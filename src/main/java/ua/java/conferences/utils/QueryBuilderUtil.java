package ua.java.conferences.utils;

import ua.java.conferences.utils.query.EventQueryBuilder;
import ua.java.conferences.utils.query.QueryBuilder;
import ua.java.conferences.utils.query.UserQueryBuilder;
import ua.java.conferences.utils.query.VisitorEventQueryBuilder;

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