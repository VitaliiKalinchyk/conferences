package ua.java.conferences.controller.actions.implementation.base;

import jakarta.servlet.http.*;
import ua.java.conferences.controller.context.AppContext;
import ua.java.conferences.controller.actions.Action;
import ua.java.conferences.dto.UserDTO;
import ua.java.conferences.exceptions.*;
import ua.java.conferences.model.services.*;
import ua.java.conferences.utils.EmailSender;

import static ua.java.conferences.controller.actions.ActionUtil.*;
import static ua.java.conferences.controller.actions.constants.ActionNames.PASSWORD_RESET_ACTION;
import static ua.java.conferences.controller.actions.constants.Pages.*;
import static ua.java.conferences.controller.actions.constants.ParameterValues.CHECK_EMAIL;
import static ua.java.conferences.controller.actions.constants.Parameters.*;
import static ua.java.conferences.utils.constants.Email.*;

public class ResetPasswordAction implements Action {
    private final UserService userService;
    private final EmailSender emailSender;

    public ResetPasswordAction(AppContext appContext) {
        userService = appContext.getUserService();
        emailSender = appContext.getEmailSender();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        return isPostMethod(request) ? executePost(request) : executeGet(request);
    }

    private String executeGet(HttpServletRequest request) {
        transferStringFromSessionToRequest(request, EMAIL);
        transferStringFromSessionToRequest(request, ERROR);
        transferStringFromSessionToRequest(request, MESSAGE);
        return RESET_PASSWORD_PAGE;
    }

    private String executePost(HttpServletRequest request) throws ServiceException {
        String email = request.getParameter(EMAIL);
        request.getSession().setAttribute(EMAIL, email);
        try {
            UserDTO user = userService.getByEmail(email);
            String newPass = userService.changePassword(user.getId());
            request.getSession().setAttribute(MESSAGE, CHECK_EMAIL);
            sendEmail(user, newPass);
        } catch (IncorrectFormatException | NoSuchUserException e) {
            request.getSession().setAttribute(ERROR, e.getMessage());
        }
        return getActionToRedirect(PASSWORD_RESET_ACTION);
    }

    private void sendEmail(UserDTO user, String newPass)  {
        String body = String.format(MESSAGE_RESET_PASSWORD, user.getName(), newPass);
        new Thread(() -> emailSender.send(SUBJECT_NOTIFICATION, body, user.getEmail())).start();
    }
}