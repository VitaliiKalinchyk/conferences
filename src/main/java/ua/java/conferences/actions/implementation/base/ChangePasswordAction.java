package ua.java.conferences.actions.implementation.base;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.*;
import ua.java.conferences.actions.*;
import ua.java.conferences.dto.response.UserResponseDTO;
import ua.java.conferences.exceptions.*;
import ua.java.conferences.services.*;

import static ua.java.conferences.actions.constants.ActionNames.CHANGE_PASSWORD_ACTION;
import static ua.java.conferences.actions.constants.Pages.*;
import static ua.java.conferences.actions.constants.ParameterValues.*;
import static ua.java.conferences.actions.constants.Parameters.*;
import static ua.java.conferences.dao.constants.DbImplementations.MYSQL;

public class ChangePasswordAction implements Action, ActionPost {

    private static final Logger logger = LoggerFactory.getLogger(ChangePasswordAction.class);

    private final UserService userService;

    public ChangePasswordAction() {
        userService = ServiceFactory.getInstance(MYSQL).getUserService();
    }

    @Override
    public String executeGet(HttpServletRequest request) {
        String path = CHANGE_PASSWORD_PAGE;
        path = getPath(request, path);
        transferStringFromSessionToRequest(request, MESSAGE);
        transferStringFromSessionToRequest(request, ERROR);
        return path;
    }

    @Override
    public String executePost(HttpServletRequest request) {
        String path = CHANGE_PASSWORD_PAGE;
        try {
            userServiceChangePassword(request);
            request.getSession().setAttribute(MESSAGE, SUCCEED_UPDATE);
        } catch (IncorrectFormatException | IncorrectPasswordException | NoSuchUserException | PasswordMatchingException e) {
            request.getSession().setAttribute(ERROR, e.getMessage());
        } catch (ServiceException e) {
            logger.error(e.getMessage());
            path = ERROR_PAGE;
        }
        request.getSession().setAttribute(CURRENT_PATH, path);
        return getControllerDirective();
    }

    private void userServiceChangePassword(HttpServletRequest request) throws ServiceException {
        long id = ((UserResponseDTO) request.getSession().getAttribute(LOGGED_USER)).getId();
        String oldPassword = request.getParameter(OLD_PASSWORD);
        String password = request.getParameter(PASSWORD);
        String confirmPassword = request.getParameter(CONFIRM_PASSWORD);
        userService.changePassword(id, oldPassword, password, confirmPassword);
    }

    private static String getControllerDirective() {
        return CONTROLLER_PAGE + "?" + ACTION + "=" + CHANGE_PASSWORD_ACTION;
    }
}