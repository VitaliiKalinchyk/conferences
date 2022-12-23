package ua.java.conferences.controller.actions.implementation.speaker;

import jakarta.servlet.http.HttpServletRequest;
import ua.java.conferences.controller.context.AppContext;
import ua.java.conferences.controller.actions.Action;
import ua.java.conferences.dto.EventDTO;
import ua.java.conferences.dto.UserDTO;
import ua.java.conferences.exceptions.*;
import ua.java.conferences.model.services.*;

import java.time.LocalDate;

import static ua.java.conferences.controller.actions.constants.Pages.*;
import static ua.java.conferences.controller.actions.constants.ParameterValues.ACCESS_DENIED;
import static ua.java.conferences.controller.actions.constants.Parameters.*;
import static ua.java.conferences.model.entities.role.Role.SPEAKER;
import static ua.java.conferences.utils.QueryBuilderUtil.*;

public class ViewEventBySpeakerAction implements Action {
    private final EventService eventService;
    private final ReportService reportService;

    public ViewEventBySpeakerAction(AppContext appContext) {
        eventService = appContext.getEventService();
        reportService = appContext.getReportService();
    }

    @Override
    public String execute(HttpServletRequest request) throws ServiceException {
        long speakerId = ((UserDTO) request.getSession().getAttribute(LOGGED_USER)).getId();
        String parameterEventId = request.getParameter(EVENT_ID);
        try {
            setAttributes(request, parameterEventId, speakerId);
        } catch (NoSuchEventException e) {
            request.setAttribute(ERROR, ACCESS_DENIED);
        }
        return VIEW_EVENT_BY_SPEAKER_PAGE;
    }

    private void setAttributes(HttpServletRequest request, String parameterEventId, long userId) throws ServiceException {
        EventDTO event = getEvent(parameterEventId, userId);
        request.setAttribute(EVENT, event);
        request.setAttribute(REPORTS, reportService.viewEventsReports(parameterEventId));
        request.setAttribute(IS_COMING, isFutureEvent(event));
    }

    private EventDTO getEvent(String parameterEventId, long userId) throws ServiceException {
        String query = eventQueryBuilder()
                .setUserIdFilter(userId)
                .getQuery();
        return eventService.getSortedByUser(query, SPEAKER).stream()
                .filter(e -> String.valueOf(e.getId()).equals(parameterEventId))
                .findAny()
                .orElseThrow(NoSuchEventException::new);
    }

    private static boolean isFutureEvent(EventDTO event) {
        return LocalDate.now().isBefore(LocalDate.parse(event.getDate()));
    }
}