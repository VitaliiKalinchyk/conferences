package ua.java.conferences.controller.actions.implementation.base;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.java.conferences.controller.context.AppContext;
import ua.java.conferences.controller.actions.Action;
import ua.java.conferences.dto.UserDTO;
import ua.java.conferences.exceptions.*;
import ua.java.conferences.model.services.*;
import ua.java.conferences.utils.EmailSender;

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
        String email = request.getParameter(EMAIL);
        try {
            UserDTO user = userService.getByEmail(email);
            String newPass = userService.changePassword(user.getId());
            request.setAttribute(MESSAGE, CHECK_EMAIL);
            sendEmail(user, newPass);
        } catch (IncorrectFormatException | NoSuchUserException e) {
            request.setAttribute(ERROR, e.getMessage());
        }
        return RESET_PASSWORD_PAGE;
    }

    private void sendEmail(UserDTO user, String newPass)  {
        String body = String.format(MESSAGE_RESET_PASSWORD, user.getName(), newPass);
        new Thread(() -> emailSender.send(SUBJECT_NOTIFICATION, body, user.getEmail())).start();
    }
}