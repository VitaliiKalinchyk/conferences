package ua.java.conferences.actions.implementation.base;

import jakarta.servlet.http.HttpServletRequest;
import ua.java.conferences.actions.Action;

import static ua.java.conferences.actions.constants.Pages.PROFILE_PAGE;

public class ProfileAction implements Action {
    @Override
    public String executeGet(HttpServletRequest request) {
        return PROFILE_PAGE;
    }
}