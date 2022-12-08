package ua.java.conferences.actions.implementation.base;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.*;
import ua.java.conferences.actions.*;
import ua.java.conferences.actions.constants.*;
import ua.java.conferences.dto.request.UserRequestDTO;
import ua.java.conferences.dto.response.UserResponseDTO;
import ua.java.conferences.exceptions.*;
import ua.java.conferences.services.*;

import static ua.java.conferences.actions.constants.ActionNames.EDIT_PROFILE_ACTION;
import static ua.java.conferences.actions.constants.Pages.*;
import static ua.java.conferences.actions.constants.Parameters.*;
import static ua.java.conferences.dao.constants.DbImplementations.MYSQL;

public class EditProfileAction implements Action, ActionPost {

    private static final Logger logger = LoggerFactory.getLogger(EditProfileAction.class);

    private final UserService userService;

    public EditProfileAction() {
        userService = ServiceFactory.getInstance(MYSQL).getUserService();
    }

    @Override
    public String executeGet(HttpServletRequest request) {
        String path = EDIT_PROFILE_PAGE;
        path = getPath(request, path);
        transferStringFromSessionToRequest(request, Parameters.MESSAGE);
        transferStringFromSessionToRequest(request, Parameters.ERROR);
        transferUserRequestDTOFromSessionToRequest(request);
        return path;
    }

    @Override
    public String executePost(HttpServletRequest request) {
        String path = EDIT_PROFILE_PAGE;
        UserResponseDTO sessionUser = (UserResponseDTO) request.getSession().getAttribute(Parameters.LOGGED_USER);
        UserRequestDTO user = getUserRequestDTO(request, sessionUser);
        try {
            userService.editProfile(user);
            request.getSession().setAttribute(Parameters.MESSAGE, ParameterValues.SUCCEED_UPDATE);
            updateSessionUser(sessionUser, user);
        } catch (IncorrectFormatException | DuplicateEmailException e) {
            request.getSession().setAttribute(Parameters.USER, user);
            request.getSession().setAttribute(Parameters.ERROR, e.getMessage());
        } catch (ServiceException e) {
            logger.error(e.getMessage());
            path = ERROR_PAGE;
        }
        request.getSession().setAttribute(Parameters.CURRENT_PATH, path);
        return getControllerDirective();
    }

    private UserRequestDTO getUserRequestDTO(HttpServletRequest request, UserResponseDTO currentUser) {
        long id = currentUser.getId();
        String email = request.getParameter(Parameters.EMAIL);
        String name = request.getParameter(Parameters.NAME);
        String surname = request.getParameter(Parameters.SURNAME);
        boolean isNotified = isNotified(request);
        return new UserRequestDTO(id, email, name, surname, isNotified);
    }

    private boolean isNotified(HttpServletRequest request) {
        String notification = request.getParameter(Parameters.NOTIFICATION);
        return notification != null && notification.equals("on");
    }

    private void updateSessionUser(UserResponseDTO currentUser, UserRequestDTO user) {
        currentUser.setEmail(user.getEmail());
        currentUser.setName(user.getName());
        currentUser.setSurname(user.getSurname());
        currentUser.setNotification(user.isNotification());
    }

    private static String getControllerDirective() {
        return CONTROLLER_PAGE + "?" + ACTION + "=" + EDIT_PROFILE_ACTION;
    }
}