package ua.java.conferences.actions.implementation.base;

import jakarta.servlet.http.HttpServletRequest;
import ua.java.conferences.actions.*;
import ua.java.conferences.dto.UserDTO;
import ua.java.conferences.exceptions.*;
import ua.java.conferences.services.*;
import ua.java.conferences.exceptions.ServiceException;

import static ua.java.conferences.actions.ActionUtil.*;
import static ua.java.conferences.actions.constants.ActionNames.*;
import static ua.java.conferences.actions.constants.Pages.*;
import static ua.java.conferences.actions.constants.Parameters.*;

public class SignInAction implements Action {

    private final UserService userService;

    public SignInAction() {
        userService = ServiceFactory.getInstance(DB_IMPLEMENTATION).getUserService();
    }

    @Override
    public String execute(HttpServletRequest request) throws ServiceException {
        return isPostMethod(request) ? executePost(request) : executeGet(request);
    }

    private String executeGet(HttpServletRequest request) {
        transferStringFromSessionToRequest(request, EMAIL);
        transferStringFromSessionToRequest(request, ERROR);
        return getPath(request);
    }

    private String executePost(HttpServletRequest request) throws ServiceException {
        String path = PROFILE_PAGE;
        String email = request.getParameter(EMAIL);
        String password = request.getParameter(PASSWORD);
        try {
            setLoggedUser(request, userService.signIn(email, password));
            return path;
        } catch (NoSuchUserException | IncorrectPasswordException e) {
            request.getSession().setAttribute(ERROR, e.getMessage());
            request.getSession().setAttribute(EMAIL, email);
            path = SIGN_IN_PAGE;
        }
        request.getSession().setAttribute(CURRENT_PATH, path);
        return getActionToRedirect(SIGN_IN_ACTION) ;
    }

    private static void setLoggedUser(HttpServletRequest request, UserDTO user) {
        request.getSession().setAttribute(LOGGED_USER, user);
        request.getSession().setAttribute(ROLE, user.getRole());
    }
}