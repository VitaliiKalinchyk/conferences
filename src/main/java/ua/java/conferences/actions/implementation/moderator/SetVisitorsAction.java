package ua.java.conferences.actions.implementation.moderator;

import jakarta.servlet.http.HttpServletRequest;
import ua.java.conferences.actions.Action;
import ua.java.conferences.exceptions.ServiceException;
import ua.java.conferences.services.*;

import static ua.java.conferences.actions.ActionUtil.*;
import static ua.java.conferences.actions.constants.ActionNames.SEARCH_EVENT_ACTION;
import static ua.java.conferences.actions.constants.Parameters.*;

public class SetVisitorsAction implements Action {
    private final EventService eventService;

    public SetVisitorsAction() {
        eventService = ServiceFactory.getInstance(DB_IMPLEMENTATION).getEventService();
    }

    @Override
    public String execute(HttpServletRequest request) throws ServiceException {
        String eventId = request.getParameter(EVENT_ID);
        String visitors = request.getParameter(VISITORS);
        eventService.setVisitorsCount(eventId, visitors);
        return getActionToRedirect(SEARCH_EVENT_ACTION, EVENT_ID, eventId);
    }
}