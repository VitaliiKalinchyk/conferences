package ua.java.conferences.controller.actions.implementation.base;

import jakarta.servlet.http.HttpServletRequest;
import ua.java.conferences.controller.actions.Action;

import static ua.java.conferences.controller.actions.constants.Pages.INDEX_PAGE;

public class DefaultAction implements Action {

    @Override
    public String execute(HttpServletRequest request) {
        return INDEX_PAGE;
    }
}