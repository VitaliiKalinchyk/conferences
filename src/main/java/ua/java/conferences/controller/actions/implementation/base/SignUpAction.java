package ua.java.conferences.controller.actions.implementation.base;

import jakarta.servlet.http.*;
import ua.java.conferences.controller.context.AppContext;
import ua.java.conferences.controller.actions.Action;
import ua.java.conferences.dto.UserDTO;
import ua.java.conferences.exceptions.*;
import ua.java.conferences.model.services.*;
import ua.java.conferences.utils.Captcha;
import ua.java.conferences.utils.EmailSender;

import static ua.java.conferences.controller.actions.ActionUtil.*;
import static ua.java.conferences.controller.actions.constants.ActionNames.SIGN_UP_ACTION;
import static ua.java.conferences.controller.actions.constants.Pages.*;
import static ua.java.conferences.controller.actions.constants.ParameterValues.SUCCEED_REGISTER;
import static ua.java.conferences.controller.actions.constants.Parameters.*;
import static ua.java.conferences.utils.constants.Email.*;

public class SignUpAction implements Action {
    private final UserService userService;
    private final EmailSender emailSender;
    private final Captcha captcha;

    public SignUpAction(AppContext appContext) {
        userService = appContext.getUserService();
        emailSender = appContext.getEmailSender();
        captcha = appContext.getCaptcha();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        return isPostMethod(request) ? executePost(request) : executeGet(request);
    }

    private String executeGet(HttpServletRequest request) {
        transferStringFromSessionToRequest(request, MESSAGE);
        transferStringFromSessionToRequest(request, ERROR);
        transferUserDTOFromSessionToRequest(request);
        return getPath(request);
    }

    private String executePost(HttpServletRequest request) throws ServiceException {
        String path = SIGN_IN_PAGE;
        UserDTO user = getUserDTO(request);
        request.getSession().setAttribute(USER, user);
        try {
            captcha.verify(request.getParameter(CAPTCHA));
            userService.add(user, request.getParameter(PASSWORD), request.getParameter(CONFIRM_PASSWORD));
            request.getSession().setAttribute(MESSAGE, SUCCEED_REGISTER);
            sendEmail(user, request);
        } catch (IncorrectFormatException | PasswordMatchingException | DuplicateEmailException | CaptchaException e) {
            request.getSession().setAttribute(ERROR, e.getMessage());
            path = SIGN_UP_PAGE;
        }
        request.getSession().setAttribute(CURRENT_PATH, path);
        return getActionToRedirect(SIGN_UP_ACTION);
    }

    private void sendEmail(UserDTO user, HttpServletRequest request) {
        String body = String.format(MESSAGE_GREETINGS, user.getName(), getURL(request));
        new Thread(() -> emailSender.send(SUBJECT_GREETINGS, body, user.getEmail())).start();
    }

    private UserDTO getUserDTO(HttpServletRequest request) {
        return UserDTO.builder()
                .email(request.getParameter(EMAIL))
                .name(request.getParameter(NAME))
                .surname(request.getParameter(SURNAME))
                .build();
    }
}