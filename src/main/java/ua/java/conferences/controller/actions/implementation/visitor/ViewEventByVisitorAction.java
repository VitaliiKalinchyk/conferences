package ua.java.conferences.controller.actions.implementation.visitor;

import jakarta.servlet.http.HttpServletRequest;
import ua.java.conferences.controller.context.AppContext;
import ua.java.conferences.controller.actions.Action;
import ua.java.conferences.dto.*;
import ua.java.conferences.exceptions.*;
import ua.java.conferences.model.services.*;

import java.time.LocalDate;

import static ua.java.conferences.controller.actions.constants.Pages.VIEW_EVENT_BY_VISITOR_PAGE;
import static ua.java.conferences.controller.actions.constants.Parameters.*;

public class ViewEventByVisitorAction implements Action {
    private final EventService eventService;
    private final ReportService reportService;
    private final UserService userService;

    public ViewEventByVisitorAction(AppContext appContext) {
        eventService = appContext.getEventService();
        reportService = appContext.getReportService();
        userService = appContext.getUserService();
    }

    @Override
    public String execute(HttpServletRequest request) throws ServiceException {
        String parameterEventId = request.getParameter(EVENT_ID);
        long userId =  ((UserDTO) request.getSession().getAttribute(LOGGED_USER)).getId();
        try {
            setAttributes(request, parameterEventId, userId);
        } catch (NoSuchEventException e) {
            request.setAttribute(ERROR, e.getMessage());
        }
        return VIEW_EVENT_BY_VISITOR_PAGE;
    }

    private void setAttributes(HttpServletRequest request, String parameterEventId, long userId) throws ServiceException {
        EventDTO event = eventService.getById(parameterEventId);
        request.setAttribute(EVENT, event);
        request.setAttribute(IS_REGISTERED, userService.isRegistered(userId, parameterEventId));
        request.setAttribute(REPORTS, reportService.viewEventsReports(parameterEventId));
        request.setAttribute(IS_COMING, isFutureEvent(event));
    }

    private static boolean isFutureEvent(EventDTO event) {
        return LocalDate.now().isBefore(LocalDate.parse(event.getDate()));
    }
}