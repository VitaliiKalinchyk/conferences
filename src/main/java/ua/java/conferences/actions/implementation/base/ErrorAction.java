package ua.java.conferences.actions.implementation.base;

import jakarta.servlet.http.HttpServletRequest;
import ua.java.conferences.actions.Action;

public class ErrorAction implements Action {
    @Override
    public String execute(HttpServletRequest req) {
        return "error.jsp";
    }
}