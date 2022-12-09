package ua.java.conferences.actions.implementation.base;

import jakarta.servlet.http.HttpServletRequest;
import ua.java.conferences.actions.*;

import static ua.java.conferences.actions.constants.Pages.INDEX_PAGE;

public class DefaultAction implements Action, ActionPost {
    @Override
    public String executeGet(HttpServletRequest request) {
        return INDEX_PAGE;
    }

    @Override
    public String executePost(HttpServletRequest request) {
        return INDEX_PAGE;
    }
}