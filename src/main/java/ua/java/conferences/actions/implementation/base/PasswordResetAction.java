package ua.java.conferences.actions.implementation.base;

import jakarta.servlet.http.HttpServletRequest;
import ua.java.conferences.actions.Action;
import ua.java.conferences.exceptions.IncorrectFormatException;
import ua.java.conferences.exceptions.NoSuchUserException;
import ua.java.conferences.exceptions.ServiceException;
import ua.java.conferences.services.ServiceFactory;
import ua.java.conferences.services.UserService;

import static ua.java.conferences.actions.constants.Parameters.*;
import static ua.java.conferences.connection.ConnectionConstants.MYSQL;

public class PasswordResetAction implements Action {

    private final UserService userService;

    public PasswordResetAction() {
        userService = ServiceFactory.getInstance(MYSQL).getUserService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        String path = "sign-in.jsp";
        String email = request.getParameter(EMAIL);
        try {
            userService.searchUser(email);
        } catch (IncorrectFormatException | NoSuchUserException e) {
            request.setAttribute(ERROR, e);
            path = "reset-password.jsp";
        } catch (ServiceException e) {
            path = "error.jsp";
        }
        //send link via email
        return path;
    }
}