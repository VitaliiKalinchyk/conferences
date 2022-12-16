package ua.java.conferences.actions.implementation.moderator;

import jakarta.servlet.http.HttpServletRequest;
import ua.java.conferences.actions.Action;
import ua.java.conferences.dto.EventDTO;
import ua.java.conferences.exceptions.*;
import ua.java.conferences.services.*;

import static ua.java.conferences.actions.ActionUtil.DB_IMPLEMENTATION;
import static ua.java.conferences.actions.constants.Pages.*;
import static ua.java.conferences.actions.constants.Parameters.*;

public class SearchEventAction implements Action {

    private final EventService eventService;

    public SearchEventAction() {
        eventService = ServiceFactory.getInstance(DB_IMPLEMENTATION).getEventService();
    }

    @Override
    public String execute(HttpServletRequest request) throws ServiceException {
        String path = VIEW_EVENT_BY_MODERATOR_PAGE;
        try {
            request.setAttribute(EVENT, getEventDTO(request));
        } catch (NoSuchEventException | IncorrectFormatException e) {
            request.setAttribute(ERROR, e.getMessage());
            request.setAttribute(TITLE, request.getParameter(TITLE));
            path = SEARCH_EVENT_PAGE;
        }
        return path;
    }

    private EventDTO getEventDTO(HttpServletRequest request) throws ServiceException {
        String title = request.getParameter(TITLE);
        String id = request.getParameter(EVENT_ID);
        return title != null ? eventService.getByTitle(title) : eventService.getById(id);
    }
}