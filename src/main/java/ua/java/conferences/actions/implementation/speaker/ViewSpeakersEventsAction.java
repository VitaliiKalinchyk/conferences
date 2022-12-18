package ua.java.conferences.actions.implementation.speaker;

import jakarta.servlet.http.HttpServletRequest;
import ua.java.conferences.actions.Action;
import ua.java.conferences.dto.EventDTO;
import ua.java.conferences.dto.UserDTO;
import ua.java.conferences.exceptions.ServiceException;
import ua.java.conferences.services.*;

import java.util.List;

import static ua.java.conferences.actions.ActionUtil.*;
import static ua.java.conferences.actions.constants.Pages.*;
import static ua.java.conferences.actions.constants.ParameterValues.*;
import static ua.java.conferences.actions.constants.Parameters.*;
import static ua.java.conferences.entities.role.Role.*;
import static ua.java.conferences.utils.QueryBuilderUtil.*;

public class ViewSpeakersEventsAction implements Action {

    private final EventService eventService;

    public ViewSpeakersEventsAction() {
        eventService = ServiceFactory.getInstance(DB_IMPLEMENTATION).getEventService();
    }

    @Override
    public String execute(HttpServletRequest request) throws ServiceException {
        long speakerId = ((UserDTO) request.getSession().getAttribute(LOGGED_USER)).getId();
        String dateFilter = request.getParameter(PAST) != null ? PASSED : UPCOMING;
        List<EventDTO> events;
        events = eventService.getSortedByUser(getQuery(speakerId, dateFilter), SPEAKER);
        request.setAttribute(EVENTS, events);
        return VIEW_SPEAKERS_EVENTS_PAGE;
    }

    private static String getQuery(long speakerId, String dateFilter) {
        return eventQueryBuilder()
                .setUserIdFilter(speakerId)
                .setDateFilter(dateFilter)
                .getQuery();
    }
}