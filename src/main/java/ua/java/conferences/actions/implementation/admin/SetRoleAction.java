package ua.java.conferences.actions.implementation.admin;

import jakarta.servlet.http.HttpServletRequest;
import ua.java.conferences.actions.Action;
import ua.java.conferences.entities.role.Role;
import ua.java.conferences.exceptions.ServiceException;
import ua.java.conferences.services.*;

import static ua.java.conferences.connection.ConnectionConstants.MYSQL;

public class SetRoleAction implements Action {

    private final UserService userService;

    public SetRoleAction() {
        userService = ServiceFactory.getInstance(MYSQL).getUserService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        String path = "view-users.jsp";
        long userId = Long.parseLong(request.getParameter("user-id"));
        int roleId = Role.valueOf(request.getParameter("role")).getValue();
        try {
            userService.setRole(userId, roleId);
        } catch (ServiceException e) {
            path = "error.jsp";
        }
        return path;
    }
}
