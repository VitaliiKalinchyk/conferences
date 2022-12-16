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

public class SignUpAction implements Action {

    private final UserService userService;

    public SignUpAction() {
        userService = ServiceFactory.getInstance(DB_IMPLEMENTATION).getUserService();
    }

    @Override
    public String execute(HttpServletRequest request) throws ServiceException {
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
            userService.add(user, request.getParameter(CONFIRM_PASSWORD));
            request.getSession().setAttribute(MESSAGE, SUCCEED_REGISTER);
        } catch (IncorrectFormatException | PasswordMatchingException | DuplicateEmailException e) {
            request.getSession().setAttribute(ERROR, e.getMessage());
            path = SIGN_UP_PAGE;
        }
        request.getSession().setAttribute(CURRENT_PATH, path);
        return getActionToRedirect(SIGN_UP_ACTION);
    }

    private UserDTO getUserDTO(HttpServletRequest request) {
        return new UserDTO.Builder()
                .setEmail(request.getParameter(EMAIL))
                .setPassword(request.getParameter(PASSWORD))
                .setName(request.getParameter(NAME))
                .setSurname(request.getParameter(SURNAME))
                .setNotification(isNotified(request))
                .get();
    }

    private static boolean isNotified(HttpServletRequest request) {
        String notification = request.getParameter(NOTIFICATION);
        return notification != null && notification.equals("on");
    }
}