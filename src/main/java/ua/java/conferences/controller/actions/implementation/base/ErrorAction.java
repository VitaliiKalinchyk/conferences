package ua.java.conferences.controller.actions.implementation.base;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.java.conferences.controller.actions.Action;

import static ua.java.conferences.controller.actions.constants.Pages.ERROR_PAGE;

/**
 * This is ErrorAction class.
 *
 * @author Vitalii Kalinchyk
 * @version 1.0
 */
public class ErrorAction implements Action {

    /**
     * @return error page
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        return ERROR_PAGE;
    }
}