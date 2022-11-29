package ua.java.conferences.actions.implementation.visitor;

import jakarta.servlet.http.HttpServletRequest;
import ua.java.conferences.actions.Action;

public class VisitorProfileAction implements Action {
    @Override
    public String execute(HttpServletRequest request) {
        return "/visitor/profile.jsp";
    }
}