package ua.java.conferences.model.utils.query;

import java.util.*;

import static ua.java.conferences.controller.actions.constants.Parameters.*;

public class EventQueryBuilder extends QueryBuilder {
    private static final Set<String> EVENT_SORT_FIELDS_SET = new HashSet<>();

    static {
        EVENT_SORT_FIELDS_SET.add(TITLE);
        EVENT_SORT_FIELDS_SET.add(DATE);
        EVENT_SORT_FIELDS_SET.add(LOCATION);
        EVENT_SORT_FIELDS_SET.add(REPORTS);
        EVENT_SORT_FIELDS_SET.add(REGISTRATIONS);
        EVENT_SORT_FIELDS_SET.add(VISITORS);
    }

    public EventQueryBuilder() {
        super("event.id");
    }

    @Override
    protected String getGroupByQuery() {
        return " GROUP BY event.id ";
    }

    @Override
    protected String checkSortField(String sortField) {
        if (EVENT_SORT_FIELDS_SET.contains(sortField.toLowerCase())) {
            return sortField;
        }
        return "event.id";
    }

    @Override
    public EventQueryBuilder setRoleFilter(String roleFilter) {
        throw new UnsupportedOperationException();
    }
}
