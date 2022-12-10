package ua.java.conferences.actions.implementation.base;

import jakarta.servlet.http.HttpServletRequest;
import ua.java.conferences.actions.*;
import ua.java.conferences.dto.request.UserRequestDTO;
import ua.java.conferences.dto.response.UserResponseDTO;
import ua.java.conferences.exceptions.*;
import ua.java.conferences.services.*;

import static ua.java.conferences.actions.ActionUtil.*;
import static ua.java.conferences.actions.constants.ActionNames.*;
import static ua.java.conferences.actions.constants.Pages.*;
import static ua.java.conferences.actions.constants.ParameterValues.*;
import static ua.java.conferences.actions.constants.Parameters.*;

public class EditProfileAction implements Action {

    private final UserService userService;

    public EditProfileAction() {
        userService = ServiceFactory.getInstance(DB_IMPLEMENTATION).getUserService();
    }

    @Override
    public String execute(HttpServletRequest request) throws ServiceException {
        return isPost(request) ? executePost(request) : executeGet(request);
    }

    private String executeGet(HttpServletRequest request) {
        transferStringFromSessionToRequest(request, MESSAGE);
        transferStringFromSessionToRequest(request, ERROR);
        transferUserRequestDTOFromSessionToRequest(request);
        return EDIT_PROFILE_PAGE;
    }

    private String executePost(HttpServletRequest request) throws ServiceException {
        UserResponseDTO sessionUser = (UserResponseDTO) request.getSession().getAttribute(LOGGED_USER);
        UserRequestDTO user = getUserRequestDTO(request, sessionUser);
        try {
            userService.editProfile(user);
            request.getSession().setAttribute(MESSAGE, SUCCEED_UPDATE);
            updateSessionUser(sessionUser, user);
        } catch (IncorrectFormatException | DuplicateEmailException e) {
            request.getSession().setAttribute(USER, user);
            request.getSession().setAttribute(ERROR, e.getMessage());
        }
        return getActionToRedirect(EDIT_PROFILE_ACTION);
    }

    private UserRequestDTO getUserRequestDTO(HttpServletRequest request, UserResponseDTO currentUser) {
        long id = currentUser.getId();
        String email = request.getParameter(EMAIL);
        String name = request.getParameter(NAME);
        String surname = request.getParameter(SURNAME);
        boolean isNotified = isNotified(request);
        return new UserRequestDTO(id, email, name, surname, isNotified);
    }

    private boolean isNotified(HttpServletRequest request) {
        String notification = request.getParameter(NOTIFICATION);
        return notification != null && notification.equals("on");
    }

    private void updateSessionUser(UserResponseDTO currentUser, UserRequestDTO user) {
        currentUser.setEmail(user.getEmail());
        currentUser.setName(user.getName());
        currentUser.setSurname(user.getSurname());
        currentUser.setNotification(user.isNotification());
    }
}