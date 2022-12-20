package ua.java.conferences.actions.implementation.base;

import jakarta.servlet.http.HttpServletRequest;
import ua.java.conferences.actions.Action;

import static ua.java.conferences.actions.constants.Pages.ERROR_PAGE;

public class ErrorAction implements Action {

    @Override
    public String execute(HttpServletRequest req) {
        return ERROR_PAGE;
    }
}