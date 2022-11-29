package ua.java.conferences.actions.implementation.admin;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.java.conferences.actions.Action;
import ua.java.conferences.exceptions.ServiceException;
import ua.java.conferences.services.*;

import static ua.java.conferences.connection.ConnectionConstants.MYSQL;

public class DeleteUserAction implements Action {

    private static final Logger logger = LoggerFactory.getLogger(DeleteUserAction.class);

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
            logger.error(e.getMessage());
            path = "error.jsp";
        }
        return path;
    }
}
