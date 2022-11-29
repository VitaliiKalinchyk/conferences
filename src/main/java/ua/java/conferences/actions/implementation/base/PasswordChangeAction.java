package ua.java.conferences.actions.implementation.base;

import jakarta.servlet.http.HttpServletRequest;
import ua.java.conferences.actions.Action;
import ua.java.conferences.dto.response.UserResponseDTO;
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
        String path = "profile.jsp";
        long id = ((UserResponseDTO) request.getSession().getAttribute("user")).getId();
        String oldPassword = request.getParameter(OLD_PASSWORD);
        String password = request.getParameter(PASSWORD);
        try {
            userService.changePassword(id, oldPassword, password);
        } catch (IncorrectFormatException | IncorrectPasswordException | NoSuchUserException e) {
            request.setAttribute(ERROR, e);
            path = "change-password.jsp";
        } catch (ServiceException e) {
            path = "error.jsp";
        }
        return path;
    }
}