package ua.java.conferences.actions.implementation.base;

import jakarta.servlet.http.HttpServletRequest;
import ua.java.conferences.actions.Action;
import ua.java.conferences.exceptions.*;
import ua.java.conferences.services.*;

import static ua.java.conferences.actions.ActionUtil.*;
import static ua.java.conferences.actions.constants.ParameterValues.*;
import static ua.java.conferences.actions.constants.Parameters.*;
import static ua.java.conferences.actions.constants.Pages.*;

public class ResetPasswordAction implements Action {

    private final UserService userService;

    public ResetPasswordAction() {
        userService = ServiceFactory.getInstance(DB_IMPLEMENTATION).getUserService();
    }

    @Override
    public String execute(HttpServletRequest request) throws ServiceException {
        String email = request.getParameter(EMAIL);
        try {
            userService.searchUser(email);
            request.setAttribute(MESSAGE, CHECK_EMAIL);
            //send link via email
        } catch (IncorrectFormatException | NoSuchUserException e) {
            request.setAttribute(ERROR, e.getMessage());
        }
        return RESET_PASSWORD_PAGE;
    }
}