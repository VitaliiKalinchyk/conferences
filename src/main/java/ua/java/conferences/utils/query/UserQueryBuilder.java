package ua.java.conferences.utils.query;

import org.slf4j.*;

import java.util.*;

import static ua.java.conferences.controller.actions.constants.Parameters.*;

public class UserQueryBuilder extends QueryBuilder {
    private static final Logger logger = LoggerFactory.getLogger(UserQueryBuilder.class);
    private static final Set<String> USER_SORT_FIELDS_SET = new HashSet<>();

    static {
        USER_SORT_FIELDS_SET.add(ID);
        USER_SORT_FIELDS_SET.add(EMAIL);
        USER_SORT_FIELDS_SET.add(NAME);
        USER_SORT_FIELDS_SET.add(SURNAME);
    }

    public UserQueryBuilder() {
        super(ID);
    }

    @Override
    protected String getGroupByQuery() {
        return "";
    }

    @Override
    protected String checkSortField(String sortField) {
        if (USER_SORT_FIELDS_SET.contains(sortField.toLowerCase())) {
            return sortField;
        }
        logger.info("wrong sort field");
        return ID;
    }

    @Override
    public UserQueryBuilder setUserIdFilter(long userIdFilter) {
        throw new UnsupportedOperationException();
    }

    @Override
    public UserQueryBuilder setDateFilter(String dateFilter) {
        throw new UnsupportedOperationException();
    }
}
