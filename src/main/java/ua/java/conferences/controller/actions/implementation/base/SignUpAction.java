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

/**
 * This is SignUpAction class. Accessible by any user. Allows to create account. Implements PRG pattern
 *
 * @author Vitalii Kalinchyk
 * @version 1.0
 */
public class SignUpAction implements Action {
    private final UserService userService;
    private final EmailSender emailSender;
    private final Captcha captcha;

    /**
     * @param appContext contains UserService, EmailSender and Captcha instances to use in action
     */
    public SignUpAction(AppContext appContext) {
        userService = appContext.getUserService();
        emailSender = appContext.getEmailSender();
        captcha = appContext.getCaptcha();
    }

    /**
     * Checks method and calls required implementation
     *
     * @param request  to get method, session and set all required attributes
     * @return path to redirect or forward by front-controller
     * @throws ServiceException to call error page in front-controller
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        return isPostMethod(request) ? executePost(request) : executeGet(request);
    }

    /**
     * Called from doGet method in front-controller. Obtains required path and transfer attributes from session
     * to request
     *
     * @param request to get message or error attribute from session and put it in request.
     * @return either sign-in page if everything is fine or sign-up if not
     */
    private String executeGet(HttpServletRequest request) {
        transferStringFromSessionToRequest(request, MESSAGE);
        transferStringFromSessionToRequest(request, ERROR);
        transferUserDTOFromSessionToRequest(request);
        return getPath(request);
    }

    /**
     * Called from doPost method in front-controller. Tries to register user. Sets different path to session depends on
     * success or not. Checks captcha. Sends email if registration was successful
     *
     * @param request to get users fields from parameters
     * @return path to redirect to executeGet method
     */
    private String executePost(HttpServletRequest request) throws ServiceException {
        String path = SIGN_IN_PAGE;
        UserDTO user = getUserDTO(request);
        request.getSession().setAttribute(USER, user);
        try {
            captcha.verify(request.getParameter(CAPTCHA));
            userService.add(user, request.getParameter(PASSWORD), request.getParameter(CONFIRM_PASSWORD));
            request.getSession().setAttribute(MESSAGE, SUCCEED_REGISTER);
            sendEmail(user, getURL(request));
        } catch (IncorrectFormatException | PasswordMatchingException | DuplicateEmailException | CaptchaException e) {
            request.getSession().setAttribute(ERROR, e.getMessage());
            path = SIGN_UP_PAGE;
        }
        request.getSession().setAttribute(CURRENT_PATH, path);
        return getActionToRedirect(SIGN_UP_ACTION);
    }

    private void sendEmail(UserDTO user, String url) {
        String body = String.format(MESSAGE_GREETINGS, user.getName(), url);
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