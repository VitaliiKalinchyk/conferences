package ua.java.conferences.controller.actions.implementation.base;

import ua.java.conferences.controller.actions.Action;

import jakarta.servlet.http.*;

import static ua.java.conferences.controller.actions.constants.Pages.SIGN_IN_PAGE;

public class SignOutAction implements Action {

    @Override
    public String execute(HttpServletRequest request) {
        request.getSession().invalidate();
        return SIGN_IN_PAGE;
    }
}