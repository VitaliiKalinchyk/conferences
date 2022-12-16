package ua.java.conferences.actions.implementation.base;

import jakarta.servlet.http.HttpServletRequest;
import ua.java.conferences.actions.*;
import ua.java.conferences.dto.UserDTO;
import ua.java.conferences.exceptions.*;
import ua.java.conferences.services.*;

import static ua.java.conferences.actions.ActionUtil.*;
import static ua.java.conferences.actions.constants.ActionNames.*;
import static ua.java.conferences.actions.constants.Pages.*;
import static ua.java.conferences.actions.constants.ParameterValues.*;
import static ua.java.conferences.actions.constants.Parameters.*;

public class ChangePasswordAction implements Action {

    private final UserService userService;

    public ChangePasswordAction() {
        userService = ServiceFactory.getInstance(DB_IMPLEMENTATION).getUserService();
    }

    @Override
    public String execute(HttpServletRequest request) throws ServiceException {
        return isPostMethod(request) ? executePost(request) : executeGet(request);
    }
    private String executeGet(HttpServletRequest request) {
        transferStringFromSessionToRequest(request, MESSAGE);
        transferStringFromSessionToRequest(request, ERROR);
        return CHANGE_PASSWORD_PAGE;
    }

    private String executePost(HttpServletRequest request) throws ServiceException {
        try {
            userServiceChangePassword(request);
            request.getSession().setAttribute(MESSAGE, SUCCEED_UPDATE);
        } catch (IncorrectFormatException | IncorrectPasswordException | NoSuchUserException | PasswordMatchingException e) {
            request.getSession().setAttribute(ERROR, e.getMessage());
        }
        return getActionToRedirect(CHANGE_PASSWORD_ACTION);
    }

    private void userServiceChangePassword(HttpServletRequest request) throws ServiceException {
        long id = ((UserDTO) request.getSession().getAttribute(LOGGED_USER)).getId();
        String oldPassword = request.getParameter(OLD_PASSWORD);
        String password = request.getParameter(PASSWORD);
        String confirmPassword = request.getParameter(CONFIRM_PASSWORD);
        userService.changePassword(id, oldPassword, password, confirmPassword);
    }
}