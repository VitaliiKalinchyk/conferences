package ua.java.conferences.actions.implementation.speaker;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.*;
import ua.java.conferences.actions.Action;
import ua.java.conferences.dto.response.*;
import ua.java.conferences.exceptions.*;
import ua.java.conferences.services.*;

import java.time.LocalDate;

import static ua.java.conferences.actions.constants.Pages.*;
import static ua.java.conferences.actions.constants.ParameterValues.*;
import static ua.java.conferences.actions.constants.Parameters.*;
import static ua.java.conferences.dao.constants.DbImplementations.MYSQL;

public class ViewEventBySpeakerAction implements Action {

    private static final Logger logger = LoggerFactory.getLogger(ViewEventBySpeakerAction.class);

    private final EventService eventService;

    private final ReportService reportService;

    public ViewEventBySpeakerAction() {
        eventService = ServiceFactory.getInstance(MYSQL).getEventService();
        reportService = ServiceFactory.getInstance(MYSQL).getReportService();
    }

    @Override
    public String executeGet(HttpServletRequest request) {
        String path = VIEW_EVENT_BY_SPEAKER_PAGE;
        String parameterEventId = request.getParameter(EVENT_ID);
        long userId =  ((UserResponseDTO) request.getSession().getAttribute(LOGGED_USER)).getId();
        try {
            setAttributes(request, parameterEventId, userId);
        } catch (NoSuchEventException e) {
            request.setAttribute(ERROR, ACCESS_DENIED);
        } catch (ServiceException e) {
            logger.error(e.getMessage());
            path = ERROR_PAGE;
        }
        return path;
    }

    private void setAttributes(HttpServletRequest request, String parameterEventId, long userId) throws ServiceException {
        long eventId = getEventId(parameterEventId);
        checkAccess(userId, eventId);
        EventResponseDTO event = eventService.view(String.valueOf(parameterEventId));
        request.setAttribute(EVENT, event);
        request.setAttribute(REPORTS, reportService.viewEventsReports(parameterEventId));
        request.setAttribute(IS_COMING, isFutureEvent(event));
    }


    private static boolean isFutureEvent(EventResponseDTO event) {
        return LocalDate.now().isBefore(LocalDate.parse(event.getDate()));
    }

    private long getEventId(String parameterEventId) throws NoSuchEventException {
        long eventId;
        try {
            eventId = Long.parseLong(parameterEventId);
        } catch (NumberFormatException e) {
            throw new NoSuchEventException();
        }
        return eventId;
    }

    private void checkAccess(long userId, long eventId) throws ServiceException {
        boolean access = eventService.viewSpeakersEvents(userId)
                .stream().mapToLong(EventResponseDTO::getId)
                .anyMatch(e -> e == eventId);
        if (!access) {
            throw new NoSuchEventException();
        }
    }
}