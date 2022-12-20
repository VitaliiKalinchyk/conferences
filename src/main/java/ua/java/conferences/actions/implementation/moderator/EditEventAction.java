package ua.java.conferences.actions.implementation.moderator;

import jakarta.servlet.http.HttpServletRequest;
import ua.java.conferences.actions.*;
import ua.java.conferences.dto.EventDTO;
import ua.java.conferences.exceptions.*;
import ua.java.conferences.services.*;

import java.time.LocalDate;

import static ua.java.conferences.actions.ActionUtil.*;
import static ua.java.conferences.actions.constants.ActionNames.*;
import static ua.java.conferences.actions.constants.Pages.*;
import static ua.java.conferences.actions.constants.Parameters.*;
import static ua.java.conferences.actions.constants.ParameterValues.*;

public class EditEventAction implements Action {
    private final EventService eventService;

    public EditEventAction() {
        eventService = ServiceFactory.getInstance(DB_IMPLEMENTATION).getEventService();
    }

    @Override
    public String execute(HttpServletRequest request) throws ServiceException {
        return isPostMethod(request) ? executePost(request) : executeGet(request);
    }

    private String executeGet(HttpServletRequest request) throws ServiceException {
        transferAttributes(request);
        String eventId = request.getParameter(EVENT_ID);
        try {
            request.setAttribute(EVENT, getEvent(eventId));
        } catch (NoSuchEventException e) {
            request.setAttribute(ERROR, e.getMessage());
        }
        return EDIT_EVENT_PAGE;
    }

    private EventDTO getEvent(String eventId) throws ServiceException {
        EventDTO event = eventService.getById(eventId);
        if (LocalDate.now().isAfter(LocalDate.parse(event.getDate()))) {
            throw new NoSuchEventException(ERROR_EVENT_EDIT);
        }
        return event;
    }

    private void transferAttributes(HttpServletRequest request) {
        transferStringFromSessionToRequest(request, MESSAGE);
        transferStringFromSessionToRequest(request, ERROR);
        transferEventDTOFromSessionToRequest(request, EVENT_NEW);
    }

    private String executePost(HttpServletRequest request) throws ServiceException {
        EventDTO event = getEventDTO(request);
        try {
            eventService.update(event);
            request.getSession().setAttribute(MESSAGE, SUCCEED_UPDATE);
        } catch (IncorrectFormatException | DuplicateTitleException e) {
            request.getSession().setAttribute(EVENT_NEW, event);
            request.getSession().setAttribute(ERROR, e.getMessage());
        }
        return getActionToRedirect(EDIT_EVENT_ACTION, EVENT_ID, String.valueOf(event.getId()));
    }

    private EventDTO getEventDTO(HttpServletRequest request) {
        return EventDTO.builder()
                .id(Long.parseLong(request.getParameter(EVENT_ID)))
                .title(request.getParameter(TITLE))
                .date(request.getParameter(DATE))
                .location(request.getParameter(LOCATION))
                .description(request.getParameter(DESCRIPTION))
                .build();
    }
}