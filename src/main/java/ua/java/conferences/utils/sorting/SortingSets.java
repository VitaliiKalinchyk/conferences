package ua.java.conferences.utils.sorting;

import ua.java.conferences.entities.role.Role;

import java.util.HashSet;
import java.util.Set;

import static ua.java.conferences.actions.constants.ParameterValues.*;
import static ua.java.conferences.actions.constants.Parameters.*;

public class SortingSets {
    private static final Set<String> DATE_SET = new HashSet<>();
    private static final Set<String> ROLE_SET = new HashSet<>();
    private static final Set<String> USER_SORT_FIELDS_SET = new HashSet<>();
    private static final Set<String> EVENT_SORT_FIELDS_SET = new HashSet<>();
    private static final Set<String> ORDER_SET = new HashSet<>();

    static {
        DATE_SET.add(PASSED);
        DATE_SET.add(UPCOMING);
        DATE_SET.add("");
    }

    static {
        ROLE_SET.add(String.valueOf(Role.VISITOR.getValue()));
        ROLE_SET.add(String.valueOf(Role.SPEAKER.getValue()));
        ROLE_SET.add(String.valueOf(Role.MODERATOR.getValue()));
        ROLE_SET.add(String.valueOf(Role.ADMIN.getValue()));
        ROLE_SET.add(ANY_ROLE);
    }

    static {
        EVENT_SORT_FIELDS_SET.add(ID);
        EVENT_SORT_FIELDS_SET.add(TITLE);
        EVENT_SORT_FIELDS_SET.add(DATE);
        EVENT_SORT_FIELDS_SET.add(LOCATION);
        EVENT_SORT_FIELDS_SET.add(REPORTS);
        EVENT_SORT_FIELDS_SET.add(REGISTRATIONS);
        EVENT_SORT_FIELDS_SET.add(VISITORS);
    }

    static {
        USER_SORT_FIELDS_SET.add(ID);
        USER_SORT_FIELDS_SET.add(EMAIL);
        USER_SORT_FIELDS_SET.add(NAME);
        USER_SORT_FIELDS_SET.add(SURNAME);
    }

    static {
        ORDER_SET.add(ASCENDING_ORDER);
        ORDER_SET.add(DESCENDING_ORDER);
    }

    public static Set<String> getDateSet() {
        return DATE_SET;
    }

    public static Set<String> getEventSortFieldsSet() {
        return EVENT_SORT_FIELDS_SET;
    }

    public static Set<String> getOrderSet() {
        return ORDER_SET;
    }

    public static Set<String> getUserSortFieldsSet() {
        return USER_SORT_FIELDS_SET;
    }

    public static Set<String> getRoleSet() {
        return ROLE_SET;
    }

    private SortingSets() {}
}