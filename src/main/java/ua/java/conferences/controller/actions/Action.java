package ua.java.conferences.controller.actions;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.java.conferences.exceptions.ServiceException;

public interface Action {
    String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException;
}