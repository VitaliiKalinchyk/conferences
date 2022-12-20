package ua.java.conferences.actions.implementation.moderator;

import jakarta.servlet.http.HttpServletRequest;
import ua.java.conferences.actions.Action;
import ua.java.conferences.dto.EventDTO;
import ua.java.conferences.exceptions.*;
import ua.java.conferences.services.*;

import static ua.java.conferences.actions.ActionUtil.*;
import static ua.java.conferences.actions.constants.ActionNames.*;
import static ua.java.conferences.actions.constants.Pages.*;
import static ua.java.conferences.actions.constants.Parameters.*;

public class CreateEventAction implements Action {
    private final EventService eventService;

    public CreateEventAction() {
        eventService = ServiceFactory.getInstance(DB_IMPLEMENTATION).getEventService();
    }

    @Override
    public String execute(HttpServletRequest request) throws ServiceException {
        return isPostMethod(request) ? executePost(request) : executeGet(request);
    }

    private String executeGet(HttpServletRequest request) {
        transferStringFromSessionToRequest(request, ERROR);
        transferEventDTOFromSessionToRequest(request, EVENT);
        return CREATE_EVENT_PAGE;
    }

    private String executePost(HttpServletRequest request) throws ServiceException {
        EventDTO event = getEventDTO(request);
        try {
            eventService.addEvent(event);
            return getActionToRedirect(SEARCH_EVENT_ACTION, TITLE, event.getTitle());
        } catch (IncorrectFormatException | PasswordMatchingException | DuplicateTitleException e) {
            request.getSession().setAttribute(EVENT, event);
            request.getSession().setAttribute(ERROR, e.getMessage());
        }
        return getActionToRedirect(CREATE_EVENT_ACTION);
    }

    private EventDTO getEventDTO(HttpServletRequest request) {
        return EventDTO.builder()
                .title(request.getParameter(TITLE))
                .date(request.getParameter(DATE))
                .location(request.getParameter(LOCATION))
                .description(request.getParameter(DESCRIPTION))
                .build();
    }
}