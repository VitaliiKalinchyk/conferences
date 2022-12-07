package ua.java.conferences.actions.implementation.admin;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.*;
import ua.java.conferences.actions.*;
import ua.java.conferences.exceptions.ServiceException;
import ua.java.conferences.services.*;

import static ua.java.conferences.actions.constants.ActionConstants.*;
import static ua.java.conferences.actions.constants.Pages.*;
import static ua.java.conferences.dao.constants.DbImplementations.MYSQL;

public class DeleteUserAction implements Action, ActionPost {

    private static final Logger logger = LoggerFactory.getLogger(DeleteUserAction.class);

    private final UserService userService;

    public DeleteUserAction() {
        userService = ServiceFactory.getInstance(MYSQL).getUserService();
    }

    @Override
    public String executeGet(HttpServletRequest request) {
        String path = SEARCH_USERS_PAGE;
        path = getPath(request, path);
        transferStringFromSessionToRequest(request, MESSAGE);
        return path;
    }

    @Override
    public String executePost(HttpServletRequest request) {
        String path = SEARCH_USERS_PAGE;
        long id = Long.parseLong(request.getParameter(USER_ID));
        try {
            userService.delete(id);
            request.getSession().setAttribute(MESSAGE, SUCCEED_DELETE);
        } catch (ServiceException e) {
            logger.error(e.getMessage());
            path = ERROR_PAGE;
        }
        request.getSession().setAttribute(CURRENT_PATH, path);
        return "controller?action=delete-user";
    }
}