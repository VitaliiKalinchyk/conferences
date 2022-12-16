package ua.java.conferences.actions.implementation.moderator;

import jakarta.servlet.http.HttpServletRequest;
import ua.java.conferences.actions.*;
import ua.java.conferences.dto.EventDTO;
import ua.java.conferences.exceptions.*;
import ua.java.conferences.services.*;

import java.time.LocalDate;

import static ua.java.conferences.actions.ActionUtil.*;
import static ua.java.conferences.actions.constants.Pages.*;
import static ua.java.conferences.actions.constants.ParameterValues.*;
import static ua.java.conferences.actions.constants.Parameters.*;

public class EditEventPageAction implements Action {

    private final EventService eventService;

    public EditEventPageAction() {
        eventService = ServiceFactory.getInstance(DB_IMPLEMENTATION).getEventService();
    }

    @Override
    public String execute(HttpServletRequest request) throws ServiceException {
        transferAttributes(request);
        String eventId = request.getParameter(EVENT_ID);
        try {
            EventDTO event = eventService.getById(eventId);
            checkDate(event);
            request.setAttribute(EVENT, event);
        } catch (NoSuchEventException e) {
            request.setAttribute(ERROR, e.getMessage());
        }
        return EDIT_EVENT_PAGE;
    }

    private void transferAttributes(HttpServletRequest request) {
        transferStringFromSessionToRequest(request, MESSAGE);
        transferStringFromSessionToRequest(request, ERROR);
        transferEventDTOFromSessionToRequest(request, EVENT_NEW);
    }

    private void checkDate(EventDTO event) throws NoSuchEventException {
        if (LocalDate.now().isAfter(LocalDate.parse(event.getDate()))) {
            throw new NoSuchEventException(ERROR_EVENT_EDIT);
        }
    }
}