package ua.java.conferences.actions.implementation.base;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.*;
import ua.java.conferences.actions.Action;
import ua.java.conferences.exceptions.*;
import ua.java.conferences.services.*;

import static ua.java.conferences.actions.constants.ParameterValues.*;
import static ua.java.conferences.actions.constants.Parameters.*;
import static ua.java.conferences.actions.constants.Pages.*;
import static ua.java.conferences.dao.constants.DbImplementations.MYSQL;

public class ResetPasswordAction implements Action {

    private static final Logger logger = LoggerFactory.getLogger(ResetPasswordAction.class);

    private final UserService userService;

    public ResetPasswordAction() {
        userService = ServiceFactory.getInstance(MYSQL).getUserService();
    }

    @Override
    public String executeGet(HttpServletRequest request) {
        String path = RESET_PASSWORD_PAGE;
        String email = request.getParameter(EMAIL);
        try {
            userService.searchUser(email);
            request.setAttribute(MESSAGE, CHECK_EMAIL);
            //send link via email
        } catch (IncorrectFormatException | NoSuchUserException e) {
            logger.error(e.getMessage());
            request.setAttribute(ERROR, e.getMessage());
        } catch (ServiceException e) {
            logger.error(e.getMessage());
            path = ERROR_PAGE;
        }
        return path;
    }
}