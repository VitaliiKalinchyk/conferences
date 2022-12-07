package ua.java.conferences.actions.implementation.admin;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.*;
import ua.java.conferences.actions.Action;
import ua.java.conferences.dto.response.UserResponseDTO;
import ua.java.conferences.exceptions.ServiceException;
import ua.java.conferences.services.*;

import java.util.List;

import static ua.java.conferences.actions.constants.ActionConstants.*;
import static ua.java.conferences.actions.constants.Pages.*;
import static ua.java.conferences.dao.constants.DbImplementations.MYSQL;

public class ViewUsersAction implements Action {

    private static final Logger logger = LoggerFactory.getLogger(ViewUsersAction.class);

    private final UserService userService;

    public ViewUsersAction() {
        userService = ServiceFactory.getInstance(MYSQL).getUserService();
    }

    @Override
    public String executeGet(HttpServletRequest request) {
        String path = VIEW_USERS_PAGE;
        try {
            List<UserResponseDTO> users = userService.viewUsers();
            request.setAttribute(USERS, users);
        } catch (ServiceException e) {
            logger.error(e.getMessage());
            path = ERROR_PAGE;
        }
        return path;
    }
}
