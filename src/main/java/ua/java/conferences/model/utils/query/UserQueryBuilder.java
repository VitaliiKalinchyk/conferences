package ua.java.conferences.model.utils.query;

import java.util.*;

import static ua.java.conferences.controller.actions.constants.Parameters.*;

public class UserQueryBuilder extends QueryBuilder {
    private static final Set<String> USER_SORT_FIELDS_SET = new HashSet<>();

    static {
        USER_SORT_FIELDS_SET.add(EMAIL);
        USER_SORT_FIELDS_SET.add(NAME);
        USER_SORT_FIELDS_SET.add(SURNAME);
    }

    public UserQueryBuilder() {
        super("id");
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
