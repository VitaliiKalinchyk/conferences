package ua.java.conferences.controller.actions.implementation.base;

import jakarta.servlet.http.HttpServletRequest;
import ua.java.conferences.controller.context.AppContext;
import ua.java.conferences.controller.actions.Action;
import ua.java.conferences.exceptions.*;
import ua.java.conferences.model.services.*;

import static ua.java.conferences.controller.actions.constants.Pages.*;
import static ua.java.conferences.controller.actions.constants.ParameterValues.CHECK_EMAIL;
import static ua.java.conferences.controller.actions.constants.Parameters.*;

public class ResetPasswordAction implements Action {
    private final UserService userService;

    public ResetPasswordAction(AppContext appContext) {
        userService = appContext.getUserService();
    }

    @Override
    public String execute(HttpServletRequest request) throws ServiceException {
        String email = request.getParameter(EMAIL);
        try {
            userService.getByEmail(email);
            request.setAttribute(MESSAGE, CHECK_EMAIL);
            //send link via email
        } catch (IncorrectFormatException | NoSuchUserException e) {
            request.setAttribute(ERROR, e.getMessage());
        }
        return RESET_PASSWORD_PAGE;
    }
}