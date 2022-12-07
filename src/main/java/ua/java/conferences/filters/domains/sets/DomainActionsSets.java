package ua.java.conferences.filters.domains.sets;

import java.util.HashSet;
import java.util.Set;

import static ua.java.conferences.actions.constants.ActionNames.*;

public final class DomainActionsSets {

    private DomainActionsSets() {}

    private static final Set<String> ANONYMOUS_USER_ACTIONS = new HashSet<>();

    private static final Set<String> LOGGED_USER_ACTIONS = new HashSet<>();

    private static final Set<String> VISITOR_ACTIONS = new HashSet<>();

    private static final Set<String> SPEAKER_ACTIONS = new HashSet<>();

    private static final Set<String> MODERATOR_ACTIONS = new HashSet<>();

    private static final Set<String> ADMIN_ACTIONS = new HashSet<>();

    static {
        ANONYMOUS_USER_ACTIONS.add(DEFAULT_ACTION);
        ANONYMOUS_USER_ACTIONS.add(SIGN_IN_ACTION);
        ANONYMOUS_USER_ACTIONS.add(SIGN_UP_ACTION);
        ANONYMOUS_USER_ACTIONS.add(PASSWORD_RESET_ACTION);
        ANONYMOUS_USER_ACTIONS.add(ERROR_ACTION);
        ANONYMOUS_USER_ACTIONS.add(SIGN_OUT_ACTION);
    }

    static {
        LOGGED_USER_ACTIONS.addAll(ANONYMOUS_USER_ACTIONS);
        LOGGED_USER_ACTIONS.add(EDIT_PROFILE_ACTION);
        LOGGED_USER_ACTIONS.add(CHANGE_PASSWORD_ACTION);
    }

    static {
        VISITOR_ACTIONS.addAll(LOGGED_USER_ACTIONS);
    }

    static {
        SPEAKER_ACTIONS.addAll(LOGGED_USER_ACTIONS);
    }

    static {
        MODERATOR_ACTIONS.addAll(LOGGED_USER_ACTIONS);
    }

    static {
        ADMIN_ACTIONS.addAll(LOGGED_USER_ACTIONS);
        ADMIN_ACTIONS.add(SEARCH_USER_ACTION);
        ADMIN_ACTIONS.add(DELETE_USER_ACTION);
        ADMIN_ACTIONS.add(SET_ROLE_ACTION);
        ADMIN_ACTIONS.add(VIEW_USERS_ACTION);
    }

    public static Set<String>  getAnonymousUserActions() {
        return ANONYMOUS_USER_ACTIONS;
    }

    public static Set<String>  getVisitorActions() {
        return VISITOR_ACTIONS;
    }

    public static Set<String>  getSpeakerActions() {
        return SPEAKER_ACTIONS;
    }

    public static Set<String>  getModeratorActions() {
        return MODERATOR_ACTIONS;
    }

    public static Set<String>  getAdminActions() {
        return ADMIN_ACTIONS;
    }
}