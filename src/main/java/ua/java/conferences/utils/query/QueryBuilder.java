package ua.java.conferences.utils.query;

import org.slf4j.*;

import java.util.*;

import static ua.java.conferences.actions.constants.ParameterValues.*;

public abstract class QueryBuilder {
    private static final Logger logger = LoggerFactory.getLogger(QueryBuilder.class);
    private final List<String> filters = new ArrayList<>();
    private String sortField;
    private String order = ASCENDING_ORDER;
    private int offset = 0;
    private int records = Integer.MAX_VALUE;

    protected QueryBuilder(String sortField) {
        this.sortField = sortField;
    }

    public QueryBuilder setIdFilter(long idFilter) {
        filters.add("user_id=" + idFilter);
        return this;
    }

    public QueryBuilder setDateFilter(String dateFilter) {
        if (dateFilter.equals(PASSED)) {
            filters.add("date < now()");
        } else if (dateFilter.equals(UPCOMING)) {
            filters.add("date > now()");
        }
        return this;
    }

    public QueryBuilder setRoleFilter(String roleFilter) {
        try {
            Integer.parseInt(roleFilter);
            filters.add("role_id=" + roleFilter);
        } catch (NumberFormatException e) {
            logger.error(e.getMessage());
        }
        return this;
    }

    public QueryBuilder setSortField(String sortField) {
        this.sortField = checkSortField(sortField);
        return this;
    }

    public QueryBuilder setOrder(String order) {
        if (order.equalsIgnoreCase(DESCENDING_ORDER)) {
            this.order = DESCENDING_ORDER;
        }
        return this;
    }

    public QueryBuilder setLimits(String offset, String records) {
        try {
            this.offset = Integer.parseInt(offset);
            this.records = Integer.parseInt(records);
        } catch (NumberFormatException e) {
            this.records = 10;
        }
        return this;
    }

    public String getQuery() {
        return getFilterQuery() + getGroupByQuery() + getSortQuery() + getLimitQuery();
    }

    public String getRecordQuery() {
        return getFilterQuery();
    }

    private String getFilterQuery() {
        if (filters.isEmpty()) {
            return "";
        }
        StringJoiner stringJoiner = new StringJoiner(" AND ", " WHERE ", " ");
        filters.forEach(stringJoiner::add);
        return stringJoiner.toString();
    }

    protected abstract String getGroupByQuery();

    private String getSortQuery() {
       return " ORDER BY " + sortField + " " + order;
    }

    private String getLimitQuery() {
        return " LIMIT " + offset + ", " + records;
    }

    protected abstract String checkSortField(String sortField);
}