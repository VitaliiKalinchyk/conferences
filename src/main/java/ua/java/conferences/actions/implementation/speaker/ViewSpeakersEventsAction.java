package ua.java.conferences.actions.implementation.speaker;

import jakarta.servlet.http.HttpServletRequest;
import ua.java.conferences.actions.Action;
import ua.java.conferences.dto.EventDTO;
import ua.java.conferences.dto.UserDTO;
import ua.java.conferences.entities.role.Role;
import ua.java.conferences.exceptions.ServiceException;
import ua.java.conferences.services.*;
import ua.java.conferences.utils.sorting.Sorting;

import java.util.List;

import static ua.java.conferences.actions.ActionUtil.*;
import static ua.java.conferences.actions.constants.Pages.*;
import static ua.java.conferences.actions.constants.ParameterValues.*;
import static ua.java.conferences.actions.constants.Parameters.*;

public class ViewSpeakersEventsAction implements Action {

    private final EventService eventService;

    public ViewSpeakersEventsAction() {
        eventService = ServiceFactory.getInstance(DB_IMPLEMENTATION).getEventService();
    }

    @Override
    public String execute(HttpServletRequest request) throws ServiceException {
        long speakerId = ((UserDTO) request.getSession().getAttribute(LOGGED_USER)).getId();
        String passed = request.getParameter(PAST);
        List<EventDTO> events;
        Sorting sorting;
        if (passed != null && passed.equals(PAST)) {
            sorting = Sorting.getEventSorting(PASSED, ID, ASCENDING_ORDER);
        } else {
            sorting = Sorting.getEventSorting(UPCOMING, ID, ASCENDING_ORDER);
        }
        events = eventService.getSortedByUser(speakerId, sorting, "0", String.valueOf(Integer.MAX_VALUE), Role.SPEAKER.name());
        request.setAttribute(EVENTS, events);
        return VIEW_SPEAKERS_EVENTS_PAGE;
    }
}