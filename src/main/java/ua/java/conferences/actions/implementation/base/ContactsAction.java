package ua.java.conferences.actions.implementation.base;

import jakarta.servlet.http.HttpServletRequest;
import ua.java.conferences.actions.Action;

public class ContactsAction implements Action {
    @Override
    public String execute(HttpServletRequest request) {
        return "contacts.jsp";
    }
}