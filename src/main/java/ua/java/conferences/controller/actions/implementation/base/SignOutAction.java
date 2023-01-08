package ua.java.conferences.controller.actions.implementation.base;

import ua.java.conferences.controller.actions.Action;

import jakarta.servlet.http.*;

import static ua.java.conferences.controller.actions.constants.Pages.SIGN_IN_PAGE;
import static ua.java.conferences.controller.actions.constants.Parameters.LOCALE;
import static ua.java.conferences.controller.actions.constants.Parameters.LOGGED_USER;

/**
 * This is SignOutAction class. Accessible by any user. Allows to sign out of web app.
 *
 * @author Vitalii Kalinchyk
 * @version 1.0
 */
public class SignOutAction implements Action {

    /**
     * Invalidates session. Saves locale and sets to new session so language will not change for user
     *
     * @param request to get session
     * @return sign in page
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        if (session.getAttribute(LOGGED_USER) != null) {
            String locale = (String) session.getAttribute(LOCALE);
            session.invalidate();
            request.getSession(true).setAttribute(LOCALE, locale);
        }
        return SIGN_IN_PAGE;
    }
}