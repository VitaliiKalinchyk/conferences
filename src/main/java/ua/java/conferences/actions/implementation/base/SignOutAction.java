package ua.java.conferences.actions.implementation.base;

import ua.java.conferences.actions.Action;

import jakarta.servlet.http.*;

import static ua.java.conferences.actions.constants.Pages.*;
import static ua.java.conferences.actions.constants.Parameters.*;

public class SignOutAction implements Action {

    @Override
    public String executeGet(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
        request.setAttribute(SEND_REDIRECT, SEND_REDIRECT);
        return SIGN_IN_PAGE;
    }
}