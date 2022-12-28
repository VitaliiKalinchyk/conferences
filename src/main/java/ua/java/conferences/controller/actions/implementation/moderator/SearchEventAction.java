package ua.java.conferences.controller.actions.implementation.moderator;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.java.conferences.controller.context.AppContext;
import ua.java.conferences.controller.actions.Action;
import ua.java.conferences.dto.EventDTO;
import ua.java.conferences.exceptions.*;
import ua.java.conferences.model.services.*;

import static ua.java.conferences.controller.actions.ActionUtil.*;
import static ua.java.conferences.controller.actions.constants.Pages.*;
import static ua.java.conferences.controller.actions.constants.Parameters.*;

public class SearchEventAction implements Action {
    private final EventService eventService;
    private final ReportService reportService;

    public SearchEventAction(AppContext appContext) {
        eventService = appContext.getEventService();
        reportService = appContext.getReportService();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        transferStringFromSessionToRequest(request, MESSAGE);
        String path = VIEW_EVENT_PAGE;
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