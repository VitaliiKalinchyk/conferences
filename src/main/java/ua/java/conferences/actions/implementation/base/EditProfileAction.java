package ua.java.conferences.actions.implementation.base;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.java.conferences.actions.Action;
import ua.java.conferences.dto.request.UserRequestDTO;
import ua.java.conferences.dto.response.UserResponseDTO;
import ua.java.conferences.exceptions.*;
import ua.java.conferences.services.*;

import static ua.java.conferences.actions.constants.Parameters.*;
import static ua.java.conferences.actions.constants.Parameters.ERROR;
import static ua.java.conferences.connection.ConnectionConstants.MYSQL;

public class EditProfileAction implements Action {

    private static final Logger logger = LoggerFactory.getLogger(EditProfileAction.class);

    private final UserService userService;

    public EditProfileAction() {
        userService = ServiceFactory.getInstance(MYSQL).getUserService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        String path = "edit-profile.jsp";
        UserResponseDTO currentUser = (UserResponseDTO) request.getSession().getAttribute("user");
        UserRequestDTO user = getUserRequestDTO(request, currentUser);
        try {
            userService.editProfile(user);
        } catch (IncorrectFormatException e) {
            setAttributes(request, user, e);
        } catch (ServiceException e) {
            logger.error(e.getMessage());
            if (e.getMessage().contains("Duplicate")) {
                request.setAttribute(ERROR, e);
            } else {
                path = "error.jsp";
            }
        }
        request.setAttribute("message", "successfully updated");
        updateCurrentUserFields(currentUser, user);
        return path;
    }

    private UserRequestDTO getUserRequestDTO(HttpServletRequest request, UserResponseDTO currentUser) {
        long id = currentUser.getId();
        String email = request.getParameter(EMAIL);
        String name = request.getParameter(NAME);
        String surname = request.getParameter(SURNAME);
        String notification = request.getParameter(NOTIFICATION);
        boolean isNotified = notification != null && notification.equals("on");
        return new UserRequestDTO(id, email, name, surname, isNotified);
    }

    private void setAttributes(HttpServletRequest request, UserRequestDTO user, IncorrectFormatException e) {
        request.setAttribute(EMAIL, user.getEmail());
        request.setAttribute(NAME, user.getName());
        request.setAttribute(SURNAME, user.getSurname());
        request.setAttribute(NOTIFICATION, user.isNotification());
        request.setAttribute(ERROR, e);
    }

    private void updateCurrentUserFields(UserResponseDTO currentUser, UserRequestDTO user) {
        currentUser.setEmail(user.getEmail());
        currentUser.setName(user.getName());
        currentUser.setSurname(user.getSurname());
        currentUser.setNotification(user.isNotification());
    }
}