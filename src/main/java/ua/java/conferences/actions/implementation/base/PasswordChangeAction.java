package ua.java.conferences.actions.implementation.base;

import jakarta.servlet.http.HttpServletRequest;
import ua.java.conferences.actions.Action;
import ua.java.conferences.exceptions.*;
import ua.java.conferences.services.*;

import static ua.java.conferences.actions.constants.Parameters.*;
import static ua.java.conferences.connection.ConnectionConstants.MYSQL;

public class PasswordChangeAction implements Action {

    private final UserService userService;

    public PasswordChangeAction() {
        userService = ServiceFactory.getInstance(MYSQL).getUserService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        String path = "sign-in.jsp";
        long id = Long.parseLong(request.getParameter(ID));
        String password = request.getParameter(ID);
        try {
            userService.changePassword(id, password);
        } catch (IncorrectFormatException e) {
            request.setAttribute(ERROR, e);
            path = "change-password.jsp";
        } catch (ServiceException e) {
            path = "error.jsp";
        }
        return path;
    }
}