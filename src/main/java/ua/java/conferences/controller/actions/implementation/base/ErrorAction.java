package ua.java.conferences.controller.actions.implementation.base;

import jakarta.servlet.http.HttpServletRequest;
import ua.java.conferences.controller.actions.Action;

import static ua.java.conferences.controller.actions.constants.Pages.ERROR_PAGE;

public class ErrorAction implements Action {

    @Override
    public String execute(HttpServletRequest req) {
        return ERROR_PAGE;
    }
}