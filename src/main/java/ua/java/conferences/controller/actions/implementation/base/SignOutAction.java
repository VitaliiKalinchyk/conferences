package ua.java.conferences.controller.actions.implementation.base;

import ua.java.conferences.controller.actions.Action;

import jakarta.servlet.http.*;

import static ua.java.conferences.controller.actions.constants.Pages.SIGN_IN_PAGE;
import static ua.java.conferences.controller.actions.constants.Parameters.LOGGED_USER;

public class SignOutAction implements Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        if (request.getSession().getAttribute(LOGGED_USER) != null) {
            request.getSession().invalidate();
        }
        return SIGN_IN_PAGE;
    }
}