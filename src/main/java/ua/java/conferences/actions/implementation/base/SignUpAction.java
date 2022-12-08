package ua.java.conferences.actions.implementation.base;

import jakarta.servlet.http.HttpServletRequest;

import org.slf4j.*;
import ua.java.conferences.actions.*;
import ua.java.conferences.dto.request.UserRequestDTO;
import ua.java.conferences.exceptions.*;
import ua.java.conferences.services.*;

import static ua.java.conferences.actions.constants.ActionNames.*;
import static ua.java.conferences.actions.constants.Pages.*;
import static ua.java.conferences.actions.constants.ParameterValues.*;
import static ua.java.conferences.actions.constants.Parameters.*;
import static ua.java.conferences.dao.constants.DbImplementations.MYSQL;

public class SignUpAction implements Action, ActionPost {

    private static final Logger logger = LoggerFactory.getLogger(SignUpAction.class);

    private final UserService userService;

    public SignUpAction() {
        userService = ServiceFactory.getInstance(MYSQL).getUserService();
    }

    @Override
    public String executeGet(HttpServletRequest request) {
        String path = getPath(request, SIGN_UP_PAGE);
        transferFromSessionToRequest(request);
        return path;
    }

    private void transferFromSessionToRequest(HttpServletRequest request) {
        transferStringFromSessionToRequest(request, MESSAGE);
        transferStringFromSessionToRequest(request, ERROR);
        transferUserRequestDTOFromSessionToRequest(request);
    }

    @Override
    public String executePost(HttpServletRequest request) {
        String path = SIGN_IN_PAGE;
        UserRequestDTO user = getUserRequestDTO(request);
        request.getSession().setAttribute(USER, user);
        try {
            userService.register(user, request.getParameter(CONFIRM_PASSWORD));
            request.getSession().setAttribute(MESSAGE, SUCCEED_REGISTER);
        } catch (IncorrectFormatException | PasswordMatchingException | DuplicateEmailException e) {
            request.getSession().setAttribute(ERROR, e.getMessage());
            path = SIGN_UP_PAGE;
        } catch (ServiceException e) {
            logger.error(e.getMessage());
            path = ERROR_PAGE;
        }
        request.getSession().setAttribute(CURRENT_PATH, path);
        return getControllerDirective();
    }

    private UserRequestDTO getUserRequestDTO(HttpServletRequest request) {
        String email = request.getParameter(EMAIL);
        String password = request.getParameter(PASSWORD);
        String name = request.getParameter(NAME);
        String surname = request.getParameter(SURNAME);
        boolean isNotified = isNotified(request);
        return new UserRequestDTO(email, password, name, surname, isNotified);
    }

    private static boolean isNotified(HttpServletRequest request) {
        String notification = request.getParameter(NOTIFICATION);
        return notification != null && notification.equals("on");
    }

    private static String getControllerDirective() {
        return CONTROLLER_PAGE + "?" + ACTION + "=" + SIGN_UP_ACTION;
    }
}