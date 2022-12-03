package ua.java.conferences.actions.implementation.admin;

import jakarta.servlet.http.HttpServletRequest;
import ua.java.conferences.actions.Action;

import static ua.java.conferences.actions.constants.Pages.VIEW_USERS_PAGE;

public class ViewUsersAction implements Action {

    @Override
    public String executeGet(HttpServletRequest request) {
        return VIEW_USERS_PAGE;
    }
}