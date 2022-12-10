package ua.java.conferences.actions.implementation.base;

import ua.java.conferences.actions.Action;

import jakarta.servlet.http.*;

import static ua.java.conferences.actions.constants.Pages.*;

public class SignOutAction implements Action {

    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
        return SIGN_IN_PAGE;
    }
}