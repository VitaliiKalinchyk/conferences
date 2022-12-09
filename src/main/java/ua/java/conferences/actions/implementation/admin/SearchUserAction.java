package ua.java.conferences.actions.implementation.admin;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.*;
import ua.java.conferences.actions.Action;
import ua.java.conferences.dto.response.UserResponseDTO;
import ua.java.conferences.exceptions.*;
import ua.java.conferences.services.*;

import static ua.java.conferences.actions.constants.Pages.*;
import static ua.java.conferences.actions.constants.Parameters.*;
import static ua.java.conferences.dao.constants.DbImplementations.MYSQL;

public class SearchUserAction implements Action {

    private static final Logger logger = LoggerFactory.getLogger(SearchUserAction.class);

    private final UserService userService;

    public SearchUserAction() {
        userService = ServiceFactory.getInstance(MYSQL).getUserService();
    }

    @Override
    public String executeGet(HttpServletRequest request) {
        String path = USER_BY_EMAIL_PAGE;
        String email = request.getParameter(EMAIL);
        try {
            UserResponseDTO user = userService.searchUser(email);
            request.setAttribute(USER, user);
        } catch (NoSuchUserException e) {
            request.setAttribute(ERROR, e.getMessage());
            path = request.getParameter(CURRENT_PAGE);
        } catch (ServiceException e) {
            logger.error(e.getMessage());
            path = ERROR_PAGE;
        }
        return path;
    }
}