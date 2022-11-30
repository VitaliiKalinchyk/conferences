package ua.java.conferences.actions.implementation.admin;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.java.conferences.actions.Action;
import ua.java.conferences.dto.response.UserResponseDTO;
import ua.java.conferences.exceptions.*;
import ua.java.conferences.services.*;

import static ua.java.conferences.connection.ConnectionConstants.MYSQL;

public class SearchUserAction implements Action {

    private static final Logger logger = LoggerFactory.getLogger(SearchUserAction.class);

    private final UserService userService;

    public SearchUserAction() {
        userService = ServiceFactory.getInstance(MYSQL).getUserService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        String path = "user-by-email.jsp";
        String email = request.getParameter("email");
        try {
            UserResponseDTO user = userService.searchUser(email);
            request.setAttribute("user", user);
        } catch (NoSuchUserException e) {
            request.setAttribute("error", e);
            path = "view-users.jsp";
        } catch (ServiceException e) {
            logger.error(e.getMessage());
            path = "error.jsp";
        }
        return path;
    }
}