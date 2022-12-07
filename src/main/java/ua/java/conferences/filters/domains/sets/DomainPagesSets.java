package ua.java.conferences.filters.domains.sets;

import java.util.*;

import static ua.java.conferences.actions.constants.Pages.*;

public final class DomainPagesSets {

    private DomainPagesSets() {}

    private static final Set<String> ANONYMOUS_USER_PAGES = new HashSet<>();

    private static final Set<String> LOGGED_USER_PAGES = new HashSet<>();

    private static final Set<String> VISITOR_PAGES = new HashSet<>();

    private static final Set<String> SPEAKER_PAGES = new HashSet<>();

    private static final Set<String> MODERATOR_PAGES = new HashSet<>();

    private static final Set<String> ADMIN_PAGES = new HashSet<>();

    static {
        ANONYMOUS_USER_PAGES.add(CONTROLLER_PAGE);
        ANONYMOUS_USER_PAGES.add(INDEX_PAGE);
        ANONYMOUS_USER_PAGES.add(ABOUT_PAGE);
        ANONYMOUS_USER_PAGES.add(CONTACTS_PAGE);
        ANONYMOUS_USER_PAGES.add(ERROR_PAGE);
        ANONYMOUS_USER_PAGES.add(SIGN_IN_PAGE);
        ANONYMOUS_USER_PAGES.add(SIGN_UP_PAGE);
        ANONYMOUS_USER_PAGES.add(RESET_PASSWORD_PAGE);
    }

    static {
        LOGGED_USER_PAGES.addAll(ANONYMOUS_USER_PAGES);
        LOGGED_USER_PAGES.add(PROFILE_PAGE);
        LOGGED_USER_PAGES.add(EDIT_PROFILE_PAGE);
        LOGGED_USER_PAGES.add(CHANGE_PASSWORD_PAGE);
    }

    static {
        VISITOR_PAGES.addAll(LOGGED_USER_PAGES);
    }

    static {
        SPEAKER_PAGES.addAll(LOGGED_USER_PAGES);
    }

    static {
        MODERATOR_PAGES.addAll(LOGGED_USER_PAGES);
    }

    static {
        ADMIN_PAGES.addAll(LOGGED_USER_PAGES);
        ADMIN_PAGES.add(VIEW_USERS_PAGE);
    }

    public static Set<String>  getAnonymousUserPages() {
        return ANONYMOUS_USER_PAGES;
    }

    public static Set<String>  getVisitorPages() {
        return VISITOR_PAGES;
    }

    public static Set<String>  getSpeakerPages() {
        return SPEAKER_PAGES;
    }

    public static Set<String>  getModeratorPages() {
        return MODERATOR_PAGES;
    }

    public static Set<String>  getAdminPages() {
        return ADMIN_PAGES;
    }
}