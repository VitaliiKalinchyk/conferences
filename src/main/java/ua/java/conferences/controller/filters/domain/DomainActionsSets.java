package ua.java.conferences.controller.filters.domain;

import java.util.*;

import static ua.java.conferences.controller.actions.constants.ActionNames.*;

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
        VISITOR_ACTIONS.add(VIEW_UPCOMING_EVENTS_ACTION);
        VISITOR_ACTIONS.add(VIEW_VISITORS_EVENTS_ACTION);
        VISITOR_ACTIONS.add(VIEW_EVENT_BY_VISITOR_ACTION);
        VISITOR_ACTIONS.add(REGISTER_OR_CANCEL_ACTION);
    }

    static {
        SPEAKER_ACTIONS.addAll(LOGGED_USER_ACTIONS);
        SPEAKER_ACTIONS.add(VIEW_SPEAKERS_EVENTS_ACTION);
        SPEAKER_ACTIONS.add(VIEW_SPEAKERS_REPORTS_ACTION);
        SPEAKER_ACTIONS.add(VIEW_EVENT_BY_SPEAKER_ACTION);
        SPEAKER_ACTIONS.add(OFFER_REPORT_ACTION);
        SPEAKER_ACTIONS.add(SET_OR_REMOVE_SPEAKER_BY_SPEAKER_ACTION);
    }

    static {
        MODERATOR_ACTIONS.addAll(LOGGED_USER_ACTIONS);
        MODERATOR_ACTIONS.add(VIEW_EVENTS_ACTION);
        MODERATOR_ACTIONS.add(CREATE_EVENT_ACTION);
        MODERATOR_ACTIONS.add(SEARCH_EVENT_ACTION);
        MODERATOR_ACTIONS.add(DELETE_EVENT_ACTION);
        MODERATOR_ACTIONS.add(EDIT_EVENT_ACTION);
        MODERATOR_ACTIONS.add(SET_VISITORS_ACTION);
        MODERATOR_ACTIONS.add(CREATE_REPORT_ACTION);
        MODERATOR_ACTIONS.add(VIEW_REPORT_ACTION);
        MODERATOR_ACTIONS.add(CHANGE_TOPIC_ACTION);
        MODERATOR_ACTIONS.add(DELETE_REPORT_ACTION);
        MODERATOR_ACTIONS.add(SET_OR_REMOVE_SPEAKER_ACTION);
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