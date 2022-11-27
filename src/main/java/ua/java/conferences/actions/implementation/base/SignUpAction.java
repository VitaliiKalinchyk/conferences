package ua.java.conferences.actions.implementation.base;

import jakarta.servlet.http.HttpServletRequest;

import ua.java.conferences.actions.Action;
import ua.java.conferences.dto.request.UserRequestDTO;
import ua.java.conferences.exceptions.IncorrectFormatException;
import ua.java.conferences.exceptions.ServiceException;
import ua.java.conferences.services.*;

import static ua.java.conferences.actions.constants.ActionConstants.*;
import static ua.java.conferences.connection.ConnectionConstants.MYSQL;

public class SignUpAction implements Action {
    private final UserService userService;

    public SignUpAction() {
        userService = ServiceFactory.getInstance(MYSQL).getUserService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        String path = "sign-in.jsp";
        String email = request.getParameter(EMAIL);
        String password = request.getParameter(PASSWORD);
        String name = request.getParameter(NAME);
        String surname = request.getParameter(SURNAME);
        boolean notification = Boolean.parseBoolean(request.getParameter(NOTIFICATION));
        UserRequestDTO user = new UserRequestDTO(0, email, password, name, surname, notification);
        request.setAttribute("user", user);
        try {
            userService.register(user);
        } catch (IncorrectFormatException e) {
            request.setAttribute("error", e);
            path = "sign-up.jsp";
        } catch (ServiceException e) {
            if (e.getMessage().contains("Duplicate")) {
                request.setAttribute("error", e);
                path = "sign-up.jsp";
            } else {
                path = "error.jsp";
            }
        }
        return path;
    }
}