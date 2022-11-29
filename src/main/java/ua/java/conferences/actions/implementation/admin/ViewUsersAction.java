package ua.java.conferences.actions.implementation.admin;

import jakarta.servlet.http.HttpServletRequest;
import ua.java.conferences.actions.Action;

public class ViewUsersAction implements Action {

    @Override
    public String execute(HttpServletRequest request) {
        return "view-users.jsp";
    }
}