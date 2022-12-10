package ua.java.conferences.actions.implementation.visitor;

import jakarta.servlet.http.HttpServletRequest;
import ua.java.conferences.actions.Action;
import ua.java.conferences.dto.response.*;
import ua.java.conferences.exceptions.ServiceException;
import ua.java.conferences.services.*;

import java.util.List;

import static ua.java.conferences.actions.ActionUtil.*;
import static ua.java.conferences.actions.constants.Pages.*;
import static ua.java.conferences.actions.constants.Parameters.*;

public class ViewVisitorsEventsAction implements Action {

    private final EventService eventService;

    public ViewVisitorsEventsAction() {
        eventService = ServiceFactory.getInstance(DB_IMPLEMENTATION).getEventService();
    }

    @Override
    public String execute(HttpServletRequest request) throws ServiceException {
        long userId = ((UserResponseDTO) request.getSession().getAttribute(LOGGED_USER)).getId();
        String passed = request.getParameter(PASSED);
        List<EventResponseDTO> events;
        if (passed != null && passed.equals(PASSED)) {
            events = eventService.viewPastUsersEvents(userId);
        } else {
            events = eventService.viewUsersEvents(userId);
        }
        request.setAttribute(EVENTS, events);
        return VIEW_VISITORS_EVENTS_PAGE;
    }
}