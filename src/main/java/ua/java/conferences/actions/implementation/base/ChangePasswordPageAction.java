package ua.java.conferences.actions.implementation.base;

import jakarta.servlet.http.HttpServletRequest;
import ua.java.conferences.actions.Action;

import static ua.java.conferences.actions.constants.Pages.CHANGE_PASSWORD_PAGE;

public class ChangePasswordPageAction implements Action {
    @Override
    public String executeGet(HttpServletRequest request) {
        return CHANGE_PASSWORD_PAGE;
    }
}