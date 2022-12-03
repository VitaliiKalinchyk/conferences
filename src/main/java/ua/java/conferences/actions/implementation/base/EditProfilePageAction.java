package ua.java.conferences.actions.implementation.base;

import jakarta.servlet.http.HttpServletRequest;
import ua.java.conferences.actions.Action;

import static ua.java.conferences.actions.constants.Pages.EDIT_PROFILE_PAGE;

public class EditProfilePageAction implements Action {
    @Override
    public String executeGet(HttpServletRequest request) {
        return EDIT_PROFILE_PAGE;
    }
}