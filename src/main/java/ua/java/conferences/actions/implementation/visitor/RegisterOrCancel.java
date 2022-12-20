package ua.java.conferences.actions.implementation.visitor;

import jakarta.servlet.http.HttpServletRequest;
import ua.java.conferences.actions.*;
import ua.java.conferences.dto.UserDTO;
import ua.java.conferences.exceptions.ServiceException;
import ua.java.conferences.services.*;

import static ua.java.conferences.actions.ActionUtil.*;
import static ua.java.conferences.actions.constants.ActionNames.*;
import static ua.java.conferences.actions.constants.ParameterValues.*;
import static ua.java.conferences.actions.constants.Parameters.*;

public class RegisterOrCancel implements Action {

    private final UserService userService;

    public RegisterOrCancel() {
        userService = ServiceFactory.getInstance(DB_IMPLEMENTATION).getUserService();
    }

    @Override
    public String execute(HttpServletRequest request) throws ServiceException {
        long userId = ((UserDTO) request.getSession().getAttribute(LOGGED_USER)).getId();
        String eventId = request.getParameter(EVENT_ID);
        String todo = request.getParameter(TODO);
        registerOrCancel(userId, eventId, todo);
        return getActionToRedirect(VIEW_EVENT_BY_VISITOR_ACTION, EVENT_ID, eventId);
    }

    private void registerOrCancel(long userId, String eventId, String todo) throws ServiceException {
        if (todo.equals(REGISTER)) {
            userService.registerForEvent(userId, eventId);
        } else if (todo.equals(CANCEL)) {
            userService.cancelRegistration(userId, eventId);
        }
    }
}
