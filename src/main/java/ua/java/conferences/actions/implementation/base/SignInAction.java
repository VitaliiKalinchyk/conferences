package ua.java.conferences.actions.implementation.base;

import jakarta.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.java.conferences.actions.Action;
import ua.java.conferences.actions.ActionPost;
import ua.java.conferences.dto.response.UserResponseDTO;
import ua.java.conferences.exceptions.*;
import ua.java.conferences.services.*;
import ua.java.conferences.exceptions.ServiceException;

import static ua.java.conferences.actions.constants.ActionConstants.*;
import static ua.java.conferences.actions.constants.Pages.*;
import static ua.java.conferences.dao.constants.DbImplementations.MYSQL;

public class SignInAction implements Action, ActionPost {

    private static final Logger logger = LoggerFactory.getLogger(SignInAction.class);

    private final UserService userService;

    public SignInAction() {
        userService = ServiceFactory.getInstance(MYSQL).getUserService();
    }

    @Override
    public String executeGet(HttpServletRequest request) {
        String path = SIGN_UP_PAGE;
        path = getPath(request, path);
        transferFromSessionToRequest(request);
        return path;
    }

    private void transferFromSessionToRequest(HttpServletRequest request) {
        transferStringFromSessionToRequest(request, EMAIL);
        transferStringFromSessionToRequest(request, ERROR);
        transferStringFromSessionToRequest(request, SEND_REDIRECT);
    }

    @Override
    public String executePost(HttpServletRequest request) {
        String path = PROFILE_PAGE;
        String email = request.getParameter(EMAIL);
        String password = request.getParameter(PASSWORD);
        UserResponseDTO user;
        try {
            user = userService.signIn(email, password);
            setLoggedUser(request, user);
            request.getSession().setAttribute(SEND_REDIRECT, SEND_REDIRECT);
        } catch (IncorrectEmailException | IncorrectPasswordException e) {
            logger.error(e.getMessage());
            setSessionAttributes(request, email, e);
            path = SIGN_IN_PAGE;
        } catch (ServiceException e) {
            logger.error(e.getMessage());
            path = ERROR_PAGE;
        }
        request.getSession().setAttribute(CURRENT_PATH, path);
        return "controller?action=sign-in";
    }

    private static void setSessionAttributes(HttpServletRequest request, String email, ServiceException e) {
        request.getSession().setAttribute(ERROR, e.getMessage());
        request.getSession().setAttribute(EMAIL, email);
    }

    private static void setLoggedUser(HttpServletRequest request, UserResponseDTO user) {
        request.getSession().setAttribute(LOGGED_USER, user);
        request.getSession().setAttribute(ROLE, user.getRole());
    }
}