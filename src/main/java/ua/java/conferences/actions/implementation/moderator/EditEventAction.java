package ua.java.conferences.actions.implementation.moderator;

import jakarta.servlet.http.HttpServletRequest;
import ua.java.conferences.actions.*;
import ua.java.conferences.dto.EventDTO;
import ua.java.conferences.exceptions.*;
import ua.java.conferences.services.*;

import static ua.java.conferences.actions.ActionUtil.*;
import static ua.java.conferences.actions.constants.ActionNames.*;
import static ua.java.conferences.actions.constants.Parameters.*;
import static ua.java.conferences.actions.constants.ParameterValues.*;

public class EditEventAction implements Action {

    private final EventService eventService;

    public EditEventAction() {
        eventService = ServiceFactory.getInstance(DB_IMPLEMENTATION).getEventService();
    }

    @Override
    public String execute(HttpServletRequest request) throws ServiceException {
        EventDTO event = getEventDTO(request);
        try {
            eventService.update(event);
            request.getSession().setAttribute(MESSAGE, SUCCEED_UPDATE);
        } catch (IncorrectFormatException | DuplicateTitleException e) {
            request.getSession().setAttribute(EVENT_NEW, event);
            request.getSession().setAttribute(ERROR, e.getMessage());
        }
        return getActionToRedirect(EDIT_EVENT_PAGE_ACTION, EVENT_ID, String.valueOf(event.getId()));
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