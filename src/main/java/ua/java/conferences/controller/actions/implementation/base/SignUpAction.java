package ua.java.conferences.controller.actions.implementation.base;

import jakarta.servlet.http.HttpServletRequest;
import ua.java.conferences.controller.context.AppContext;
import ua.java.conferences.controller.actions.Action;
import ua.java.conferences.dto.UserDTO;
import ua.java.conferences.exceptions.*;
import ua.java.conferences.model.services.*;

import static ua.java.conferences.controller.actions.ActionUtil.*;
import static ua.java.conferences.controller.actions.constants.ActionNames.SIGN_UP_ACTION;
import static ua.java.conferences.controller.actions.constants.Pages.*;
import static ua.java.conferences.controller.actions.constants.ParameterValues.SUCCEED_REGISTER;
import static ua.java.conferences.controller.actions.constants.Parameters.*;

public class SignUpAction implements Action {
    private final UserService userService;

    public SignUpAction(AppContext appContext) {
        userService = appContext.getUserService();
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
            userService.add(user, request.getParameter(PASSWORD), request.getParameter(CONFIRM_PASSWORD));
            request.getSession().setAttribute(MESSAGE, SUCCEED_REGISTER);
        } catch (IncorrectFormatException | PasswordMatchingException | DuplicateEmailException e) {
            request.getSession().setAttribute(ERROR, e.getMessage());
            path = SIGN_UP_PAGE;
        }
        request.getSession().setAttribute(CURRENT_PATH, path);
        return getActionToRedirect(SIGN_UP_ACTION);
    }

    private UserDTO getUserDTO(HttpServletRequest request) {
        return UserDTO.builder()
                .email(request.getParameter(EMAIL))
                .name(request.getParameter(NAME))
                .surname(request.getParameter(SURNAME))
                .notification(isNotified(request))
                .build();
    }

    private static boolean isNotified(HttpServletRequest request) {
        String notification = request.getParameter(NOTIFICATION);
        return notification != null && notification.equals("on");
    }
}