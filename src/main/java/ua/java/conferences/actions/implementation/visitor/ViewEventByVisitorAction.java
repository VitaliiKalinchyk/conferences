package ua.java.conferences.actions.implementation.visitor;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.*;
import ua.java.conferences.actions.*;
import ua.java.conferences.dto.response.*;
import ua.java.conferences.exceptions.ServiceException;
import ua.java.conferences.services.*;

import java.time.LocalDate;
import java.util.List;

import static ua.java.conferences.actions.constants.Parameters.*;
import static ua.java.conferences.actions.constants.Pages.*;
import static ua.java.conferences.dao.constants.DbImplementations.MYSQL;

public class ViewEventByVisitorAction implements Action {

    private static final Logger logger = LoggerFactory.getLogger(ViewEventByVisitorAction.class);

    private final EventService eventService;

    private final ReportService reportService;

    private final UserService userService;

    public ViewEventByVisitorAction() {
        eventService = ServiceFactory.getInstance(MYSQL).getEventService();
        reportService = ServiceFactory.getInstance(MYSQL).getReportService();
        userService = ServiceFactory.getInstance(MYSQL).getUserService();
    }

    @Override
    public String executeGet(HttpServletRequest request) {
        String path = VIEW_EVENT_BY_VISITOR_PAGE;
        long eventId = Long.parseLong(request.getParameter(EVENT_ID));
        long userId =  ((UserResponseDTO) request.getSession().getAttribute(LOGGED_USER)).getId();
        try {
            EventResponseDTO event = eventService.view(eventId);
            request.setAttribute(EVENT, event);
            boolean isRegistered = userService.isRegistered(userId, eventId);
            request.setAttribute(IS_REGISTERED, isRegistered);
            List<ReportResponseDTO> reports = reportService.viewEventsReports(eventId);
            request.setAttribute(REPORTS, reports);
            boolean futureEvent = LocalDate.now().isBefore(LocalDate.parse(event.getDate()));
            request.setAttribute(IS_COMING, futureEvent);
        } catch (ServiceException e) {
            logger.error(e.getMessage());
            path = ERROR_PAGE;
        }
        return path;
    }
}