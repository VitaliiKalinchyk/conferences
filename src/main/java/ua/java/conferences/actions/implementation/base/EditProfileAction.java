package ua.java.conferences.actions.implementation.base;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.*;
import ua.java.conferences.actions.Action;
import ua.java.conferences.actions.ActionPost;
import ua.java.conferences.dto.request.UserRequestDTO;
import ua.java.conferences.dto.response.UserResponseDTO;
import ua.java.conferences.exceptions.*;
import ua.java.conferences.services.*;

import static ua.java.conferences.actions.constants.ActionConstants.*;
import static ua.java.conferences.actions.constants.Pages.EDIT_PROFILE_PAGE;
import static ua.java.conferences.actions.constants.Pages.ERROR_PAGE;
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
        transferStringFromSessionToRequest(request, MESSAGE);
        transferStringFromSessionToRequest(request, ERROR);
        transferUserRequestDTOFromSessionToRequest(request);
        return path;
    }

    @Override
    public String executePost(HttpServletRequest request) {
        String path = EDIT_PROFILE_PAGE;
        UserResponseDTO sessionUser = (UserResponseDTO) request.getSession().getAttribute(LOGGED_USER);
        UserRequestDTO user = getUserRequestDTO(request, sessionUser);
        try {
            userService.editProfile(user);
            request.getSession().setAttribute(MESSAGE, SUCCEED_UPDATE);
            updateSessionUser(sessionUser, user);
        } catch (IncorrectFormatException | DuplicateEmailException e) {
            request.getSession().setAttribute(USER, user);
            request.getSession().setAttribute(ERROR, e.getMessage());
        } catch (ServiceException e) {
            logger.error(e.getMessage());
            path = ERROR_PAGE;
        }
        request.getSession().setAttribute(CURRENT_PATH, path);
        return "controller?action=edit-profile";
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