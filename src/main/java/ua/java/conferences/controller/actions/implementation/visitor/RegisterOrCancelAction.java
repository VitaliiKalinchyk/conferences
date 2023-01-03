package ua.java.conferences.controller.actions.implementation.visitor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.java.conferences.controller.context.AppContext;
import ua.java.conferences.controller.actions.Action;
import ua.java.conferences.dto.UserDTO;
import ua.java.conferences.exceptions.ServiceException;
import ua.java.conferences.model.services.*;

import static ua.java.conferences.controller.actions.ActionUtil.*;
import static ua.java.conferences.controller.actions.constants.ActionNames.VIEW_EVENT_BY_VISITOR_ACTION;
import static ua.java.conferences.controller.actions.constants.ParameterValues.*;
import static ua.java.conferences.controller.actions.constants.Parameters.*;

public class RegisterOrCancelAction implements Action {

    private final UserService userService;

    public RegisterOrCancelAction(AppContext appContext) {
        userService = appContext.getUserService();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        long userId = ((UserDTO) request.getSession().getAttribute(LOGGED_USER)).getId();
        String eventId = request.getParameter(EVENT_ID);
        registerOrCancel(userId, eventId, request.getParameter(TODO));
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
