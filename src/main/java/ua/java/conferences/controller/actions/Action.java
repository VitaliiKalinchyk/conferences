package ua.java.conferences.controller.actions;

import jakarta.servlet.http.HttpServletRequest;
import ua.java.conferences.exceptions.ServiceException;

public interface Action {
    String execute(HttpServletRequest request) throws ServiceException;
}