package ua.java.conferences.actions.implementation.admin;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.java.conferences.actions.Action;
import ua.java.conferences.entities.role.Role;
import ua.java.conferences.exceptions.ServiceException;
import ua.java.conferences.services.*;

import static ua.java.conferences.connection.ConnectionConstants.MYSQL;

public class SetRoleAction implements Action {

    private static final Logger logger = LoggerFactory.getLogger(SetRoleAction.class);

    private final UserService userService;

    public SetRoleAction() {
        userService = ServiceFactory.getInstance(MYSQL).getUserService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        String path = "user-by-email.jsp";
        long userId = Long.parseLong(request.getParameter("user-id"));
        int roleId = Role.valueOf(request.getParameter("role")).getValue();
        try {
            userService.setRole(userId, roleId);
            request.setAttribute("user", userService.view(userId));
        } catch (ServiceException e) {
            logger.error(e.getMessage());
            path = "error.jsp";
        }
        return path;
    }
}
