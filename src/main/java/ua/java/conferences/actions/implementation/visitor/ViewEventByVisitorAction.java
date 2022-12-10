package ua.java.conferences.actions.implementation.visitor;

import jakarta.servlet.http.HttpServletRequest;
import ua.java.conferences.actions.*;
import ua.java.conferences.dto.response.*;
import ua.java.conferences.exceptions.*;
import ua.java.conferences.services.*;

import java.time.LocalDate;

import static ua.java.conferences.actions.ActionUtil.*;
import static ua.java.conferences.actions.constants.Parameters.*;
import static ua.java.conferences.actions.constants.Pages.*;

public class ViewEventByVisitorAction implements Action {

    private final EventService eventService;

    private final ReportService reportService;

    private final UserService userService;

    public ViewEventByVisitorAction() {
        eventService = ServiceFactory.getInstance(DB_IMPLEMENTATION).getEventService();
        reportService = ServiceFactory.getInstance(DB_IMPLEMENTATION).getReportService();
        userService = ServiceFactory.getInstance(DB_IMPLEMENTATION).getUserService();
    }

    @Override
    public String execute(HttpServletRequest request) throws ServiceException {
        String parameterEventId = request.getParameter(EVENT_ID);
        long userId =  ((UserResponseDTO) request.getSession().getAttribute(LOGGED_USER)).getId();
        try {
            setAttributes(request, parameterEventId, userId);
        } catch (NoSuchEventException e) {
            request.setAttribute(ERROR, e.getMessage());
        }
        return VIEW_EVENT_BY_VISITOR_PAGE;
    }

    private void setAttributes(HttpServletRequest request, String parameterEventId, long userId) throws ServiceException {
        EventResponseDTO event = eventService.view(parameterEventId);
        request.setAttribute(EVENT, event);
        request.setAttribute(IS_REGISTERED, userService.isRegistered(userId, parameterEventId));
        request.setAttribute(REPORTS, reportService.viewEventsReports(parameterEventId));
        request.setAttribute(IS_COMING, isFutureEvent(event));
    }

    private static boolean isFutureEvent(EventResponseDTO event) {
        return LocalDate.now().isBefore(LocalDate.parse(event.getDate()));
    }
}