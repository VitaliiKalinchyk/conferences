package ua.java.conferences.actions.implementation.base;

import jakarta.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.java.conferences.actions.Action;
import ua.java.conferences.dto.response.UserResponseDTO;
import ua.java.conferences.exceptions.*;
import ua.java.conferences.services.*;
import ua.java.conferences.exceptions.ServiceException;

import static ua.java.conferences.actions.constants.Parameters.*;
import static ua.java.conferences.connection.ConnectionConstants.MYSQL;

public class SignInAction implements Action {

    private static final Logger logger = LoggerFactory.getLogger(SignInAction.class);

    private final UserService userService;

    public SignInAction() {
        userService = ServiceFactory.getInstance(MYSQL).getUserService();
    }
    @Override
    public String execute(HttpServletRequest request) {
        String path;
        String email = request.getParameter(EMAIL);
        String password = request.getParameter(PASSWORD);
        UserResponseDTO user;
        try {
            user = userService.signIn(email, password);
            setSessionAttributes(request, user);
            path = "profile.jsp";
        } catch (IncorrectEmailException | IncorrectPasswordException e) {
            logger.error(e.getMessage());
            request.setAttribute("error", e);
            path = "sign-in.jsp";
        } catch (ServiceException e) {
            logger.error(e.getMessage());
            path = "error.jsp";
        }
        return path;
    }

    private static void setSessionAttributes(HttpServletRequest request, UserResponseDTO user) {
        request.getSession().setAttribute(USER, user);
        request.getSession().setAttribute(ROLE, user.getRole());
    }
}