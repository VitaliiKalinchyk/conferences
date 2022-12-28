package ua.java.conferences.controller.actions.implementation.moderator;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.java.conferences.controller.context.AppContext;
import ua.java.conferences.controller.actions.Action;
import ua.java.conferences.exceptions.ServiceException;
import ua.java.conferences.model.services.*;

import static ua.java.conferences.controller.actions.ActionUtil.*;
import static ua.java.conferences.controller.actions.constants.ActionNames.SEARCH_EVENT_ACTION;
import static ua.java.conferences.controller.actions.constants.Parameters.*;

public class SetVisitorsAction implements Action {
    private final EventService eventService;

    public SetVisitorsAction(AppContext appContext) {
        eventService = appContext.getEventService();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String eventId = request.getParameter(EVENT_ID);
        String visitors = request.getParameter(VISITORS);
        eventService.setVisitorsCount(eventId, visitors);
        return getActionToRedirect(SEARCH_EVENT_ACTION, EVENT_ID, eventId);
    }
}