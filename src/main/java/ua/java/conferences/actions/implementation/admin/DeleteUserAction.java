package ua.java.conferences.actions.implementation.admin;

import jakarta.servlet.http.HttpServletRequest;
import ua.java.conferences.actions.Action;
import ua.java.conferences.exceptions.ServiceException;
import ua.java.conferences.services.*;

import static ua.java.conferences.connection.ConnectionConstants.MYSQL;

public class DeleteUserAction implements Action {

    private final UserService userService;

    public DeleteUserAction() {
        userService = ServiceFactory.getInstance(MYSQL).getUserService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        String path = "view-users.jsp";
        long id = Long.parseLong(request.getParameter("user-id"));
        try {
            userService.delete(id);
        } catch (ServiceException e) {
            path = "error.jsp";
        }
        return path;
    }
}
