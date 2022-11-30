package ua.java.conferences.actions;

import ua.java.conferences.actions.implementation.admin.*;
import ua.java.conferences.actions.implementation.base.*;

import java.util.*;

public final class ActionFactory {

    private static final ActionFactory ACTION_FACTORY = new ActionFactory();

    private static final Map<String, Action> ACTION_MAP = new HashMap<>();

    static {
        ACTION_MAP.put("default", new DefaultAction());
        ACTION_MAP.put("sign-in", new SignInAction());
        ACTION_MAP.put("sign-up", new SignUpAction());
        ACTION_MAP.put("error", new ErrorAction());

        ACTION_MAP.put("profile", new ProfileAction());
        ACTION_MAP.put("edit-profile-page", new EditProfilePageAction());
        ACTION_MAP.put("edit-profile", new EditProfileAction());
        ACTION_MAP.put("sign-out", new SignOutAction());
        ACTION_MAP.put("password-reset", new PasswordResetAction());
        ACTION_MAP.put("change-password", new PasswordChangeAction());
        ACTION_MAP.put("change-password-page", new PasswordChangePageAction());

        ACTION_MAP.put("view-users", new ViewUsersAction());
        ACTION_MAP.put("search-user", new SearchUserAction());
        ACTION_MAP.put("delete-user", new DeleteUserAction());
        ACTION_MAP.put("set-role", new SetRoleAction());

    }

    private ActionFactory() {}

    public static ActionFactory getActionFactory() {
        return ACTION_FACTORY;
    }

    public Action createAction(String actionName) {
        return ACTION_MAP.getOrDefault(actionName, new DefaultAction());
    }
}