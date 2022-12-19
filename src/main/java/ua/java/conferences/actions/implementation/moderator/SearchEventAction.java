package ua.java.conferences.actions.implementation.moderator;

import jakarta.servlet.http.HttpServletRequest;
import ua.java.conferences.actions.Action;
import ua.java.conferences.dto.EventDTO;
import ua.java.conferences.exceptions.*;
import ua.java.conferences.services.*;

import static ua.java.conferences.actions.ActionUtil.DB_IMPLEMENTATION;
import static ua.java.conferences.actions.ActionUtil.transferStringFromSessionToRequest;
import static ua.java.conferences.actions.constants.Pages.*;
import static ua.java.conferences.actions.constants.Parameters.*;

public class SearchEventAction implements Action {

    private final EventService eventService;
    private final ReportService reportService;

    public SearchEventAction() {
        eventService = ServiceFactory.getInstance(DB_IMPLEMENTATION).getEventService();
        reportService = ServiceFactory.getInstance(DB_IMPLEMENTATION).getReportService();
    }

    @Override
    public String execute(HttpServletRequest request) throws ServiceException {
        transferStringFromSessionToRequest(request, MESSAGE);
        String path = VIEW_EVENT_BY_MODERATOR_PAGE;
        try {
            EventDTO event = getEventDTO(request);
            request.setAttribute(EVENT, event);
            request.setAttribute(REPORTS, reportService.viewEventsReports(String.valueOf(event.getId())));
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