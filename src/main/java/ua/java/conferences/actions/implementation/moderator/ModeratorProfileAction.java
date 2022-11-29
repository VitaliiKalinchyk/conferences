package ua.java.conferences.actions.implementation.moderator;

import jakarta.servlet.http.HttpServletRequest;
import ua.java.conferences.actions.Action;

public class ModeratorProfileAction implements Action {
    @Override
    public String execute(HttpServletRequest request) {
        return "/moderator/profile.jsp";
    }
}