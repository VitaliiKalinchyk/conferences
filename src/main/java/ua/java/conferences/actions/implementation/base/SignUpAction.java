package ua.java.conferences.actions.implementation.base;

import jakarta.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.java.conferences.actions.Action;
import ua.java.conferences.dto.request.UserRequestDTO;
import ua.java.conferences.exceptions.DuplicateEmailException;
import ua.java.conferences.exceptions.IncorrectFormatException;
import ua.java.conferences.exceptions.ServiceException;
import ua.java.conferences.services.*;

import static ua.java.conferences.actions.constants.Parameters.*;
import static ua.java.conferences.connection.ConnectionConstants.MYSQL;

public class SignUpAction implements Action {

    private static final Logger logger = LoggerFactory.getLogger(SignUpAction.class);

    private final UserService userService;

    public SignUpAction() {
        userService = ServiceFactory.getInstance(MYSQL).getUserService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        String path = "sign-in.jsp";
        UserRequestDTO user = getUserRequestDTO(request);
        request.setAttribute(USER, user);
        try {
            userService.register(user);
        } catch (IncorrectFormatException | DuplicateEmailException e) {
            request.setAttribute(ERROR, e.getMessage());
            path = "sign-up.jsp";
        } catch (ServiceException e) {
            logger.error(e.getMessage());
            path = "error.jsp";
        }
        return path;
    }

    private static UserRequestDTO getUserRequestDTO(HttpServletRequest request) {
        String email = request.getParameter(EMAIL);
        String password = request.getParameter(PASSWORD);
        String name = request.getParameter(NAME);
        String surname = request.getParameter(SURNAME);
        String notification = request.getParameter(NOTIFICATION);
        boolean isNotified = notification != null && notification.equals("on");
        return new UserRequestDTO(email, password, name, surname, isNotified);
    }
}