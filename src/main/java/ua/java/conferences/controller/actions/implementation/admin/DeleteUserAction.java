package ua.java.conferences.controller.actions.implementation.admin;

import jakarta.servlet.http.*;
import org.slf4j.*;
import ua.java.conferences.controller.context.AppContext;
import ua.java.conferences.controller.actions.Action;
import ua.java.conferences.exceptions.*;
import ua.java.conferences.model.services.*;

import static ua.java.conferences.controller.actions.ActionUtil.*;
import static ua.java.conferences.controller.actions.constants.ActionNames.*;
import static ua.java.conferences.controller.actions.constants.Pages.*;
import static ua.java.conferences.controller.actions.constants.Parameters.*;
import static ua.java.conferences.controller.actions.constants.ParameterValues.*;

public class DeleteUserAction implements Action {
    private static final Logger logger = LoggerFactory.getLogger(DeleteUserAction.class);
    private final UserService userService;

    public DeleteUserAction(AppContext appContext) {
        userService = appContext.getUserService();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        return isPostMethod(request) ? executePost(request) : executeGet(request);
    }

    private String executeGet(HttpServletRequest request) {
        transferStringFromSessionToRequest(request, MESSAGE);
        return SEARCH_USER_PAGE;
    }

    private String executePost(HttpServletRequest request) throws ServiceException {
        try {
            userService.delete(request.getParameter(USER_ID));
            request.getSession().setAttribute(MESSAGE, SUCCEED_DELETE);
        } catch (NoSuchUserException e) {
            logger.error("Couldn't delete user - no such user");
        }
        return getActionToRedirect(DELETE_USER_ACTION);
    }
}