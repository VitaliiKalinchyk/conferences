package ua.java.conferences.actions;

import jakarta.servlet.http.HttpServletRequest;
import ua.java.conferences.actions.implementation.*;

import java.util.*;

public final class ActionFactory {

    private static final ActionFactory ACTION_FACTORY = new ActionFactory();

    private static final Map<String, Action> ACTION_MAP = new HashMap<>();

    static {
        ACTION_MAP.put("/sign-in", new SignInAction());
        ACTION_MAP.put("/sign-out", new SignOutAction());
        ACTION_MAP.put("/sign-up", new SignUpAction());
        ACTION_MAP.put("/error", new ErrorAction());
    }

    private ActionFactory() {}

    public static ActionFactory getActionFactory() {
        return ACTION_FACTORY;
    }

    public Action createAction(HttpServletRequest request) {
        String url = request.getRequestURI().substring(request.getContextPath().length());
        Action action = ACTION_MAP.get(url);
        if (Objects.isNull(action)) {
            return new ErrorAction();
        }
        return action;
    }
}