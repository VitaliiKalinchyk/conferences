package ua.java.conferences.actions;

import ua.java.conferences.actions.implementation.admin.*;
import ua.java.conferences.actions.implementation.base.*;

import java.util.*;

import static ua.java.conferences.actions.constants.ActionNames.*;

public final class ActionFactory {

    private static final ActionFactory ACTION_FACTORY = new ActionFactory();

    private static final Map<String, Action> ACTION_MAP = new HashMap<>();

    static {
        ACTION_MAP.put(DEFAULT_ACTION, new DefaultAction());
        ACTION_MAP.put(SIGN_IN_ACTION, new SignInAction());
        ACTION_MAP.put(SIGN_UP_ACTION, new SignUpAction());
        ACTION_MAP.put(PASSWORD_RESET_ACTION, new ResetPasswordAction());
        ACTION_MAP.put(ERROR_ACTION, new ErrorAction());

        ACTION_MAP.put(SIGN_OUT_ACTION, new SignOutAction());
        ACTION_MAP.put(EDIT_PROFILE_ACTION, new EditProfileAction());
        ACTION_MAP.put(CHANGE_PASSWORD_ACTION, new ChangePasswordAction());

        ACTION_MAP.put(VIEW_USERS_ACTION, new ViewUsersAction());
        ACTION_MAP.put(SEARCH_USER_ACTION, new SearchUserAction());
        ACTION_MAP.put(DELETE_USER_ACTION, new DeleteUserAction());
        ACTION_MAP.put(SET_ROLE_ACTION, new SetRoleAction());

    }

    private ActionFactory() {}

    public static ActionFactory getActionFactory() {
        return ACTION_FACTORY;
    }

    public Action createAction(String actionName) {
        return ACTION_MAP.getOrDefault(actionName, new DefaultAction());
    }

    public ActionPost createActionPost(String actionName) {
        return (ActionPost) ACTION_MAP.getOrDefault(actionName, new DefaultAction());
    }
}