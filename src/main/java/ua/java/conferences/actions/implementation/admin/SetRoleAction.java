package ua.java.conferences.actions.implementation.admin;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.*;
import ua.java.conferences.actions.Action;
import ua.java.conferences.entities.role.Role;
import ua.java.conferences.exceptions.ServiceException;
import ua.java.conferences.services.*;

import static ua.java.conferences.actions.constants.ActionConstants.*;
import static ua.java.conferences.actions.constants.Pages.*;
import static ua.java.conferences.dao.constants.DbImplementations.MYSQL;

public class SetRoleAction implements Action {

    private static final Logger logger = LoggerFactory.getLogger(SetRoleAction.class);

    private final UserService userService;

    public SetRoleAction() {
        userService = ServiceFactory.getInstance(MYSQL).getUserService();
    }

    @Override
    public String executeGet(HttpServletRequest request) {
        String path = USER_BY_EMAIL_PAGE;
        long userId = Long.parseLong(request.getParameter(USER_ID));
        int roleId = Role.valueOf(request.getParameter(ROLE)).getValue();
        try {
            userService.setRole(userId, roleId);
            request.setAttribute(USER, userService.view(userId));
        } catch (ServiceException e) {
            logger.error(e.getMessage());
            path = ERROR_PAGE;
        }
        return path;
    }
}
