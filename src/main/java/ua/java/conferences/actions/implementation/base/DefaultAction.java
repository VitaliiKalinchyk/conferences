package ua.java.conferences.actions.implementation.base;

import jakarta.servlet.http.HttpServletRequest;
import ua.java.conferences.actions.*;

import static ua.java.conferences.actions.constants.Pages.INDEX_PAGE;

public class DefaultAction implements Action {
    @Override
    public String execute(HttpServletRequest request) {
        return INDEX_PAGE;
    }
}