package ua.java.conferences.actions.implementation.speaker;

import jakarta.servlet.http.HttpServletRequest;
import ua.java.conferences.actions.*;
import ua.java.conferences.dto.*;
import ua.java.conferences.exceptions.*;
import ua.java.conferences.services.*;

import java.time.LocalDate;

import static ua.java.conferences.actions.ActionUtil.*;
import static ua.java.conferences.actions.constants.ActionNames.*;
import static ua.java.conferences.actions.constants.Pages.*;
import static ua.java.conferences.actions.constants.ParameterValues.*;
import static ua.java.conferences.actions.constants.Parameters.*;
import static ua.java.conferences.entities.role.Role.*;
import static ua.java.conferences.utils.QueryBuilderUtil.*;

public class OfferReportAction implements Action {
    private final ReportService reportService;
    private final EventService eventService;

    public OfferReportAction() {
        reportService = ServiceFactory.getInstance(DB_IMPLEMENTATION).getReportService();
        eventService = ServiceFactory.getInstance(DB_IMPLEMENTATION).getEventService();
    }

    @Override
    public String execute(HttpServletRequest request) throws ServiceException {
        return isPostMethod(request) ? executePost(request) : executeGet(request);
    }

    private String executeGet(HttpServletRequest request) throws ServiceException {
        transferAttributes(request);
        long speakerId = ((UserDTO) request.getSession().getAttribute(LOGGED_USER)).getId();
        try {
            EventDTO event = getEvent(request.getParameter(EVENT_ID), speakerId);
            request.setAttribute(EVENT, event);
        } catch (NoSuchEventException e) {
            request.setAttribute(ERROR, OFFER_FORBIDDEN);
        }
        return OFFER_REPORT_PAGE;
    }

    private EventDTO getEvent(String parameterEventId, long userId) throws ServiceException {
        String query = eventQueryBuilder()
                .setUserIdFilter(userId)
                .getQuery();
        return eventService.getSortedByUser(query, SPEAKER).stream()
                .filter(e -> String.valueOf(e.getId()).equals(parameterEventId))
                .filter(e -> LocalDate.now().isBefore(LocalDate.parse(e.getDate())))
                .findAny()
                .orElseThrow(NoSuchEventException::new);
    }

    private void transferAttributes(HttpServletRequest request) {
        transferStringFromSessionToRequest(request, MESSAGE);
        transferStringFromSessionToRequest(request, ERROR);
    }

    private String executePost(HttpServletRequest request) throws ServiceException {
        ReportDTO reportDTO = getReportDTO(request);
        try {
            reportService.addReport(reportDTO);
            request.getSession().setAttribute(MESSAGE, SUCCEED_ADD);
        } catch (IncorrectFormatException e) {
            request.getSession().setAttribute(ERROR, e.getMessage());
        }
        return getActionToRedirect(OFFER_REPORT_ACTION, EVENT_ID, String.valueOf(reportDTO.getEventId()));
    }

    private ReportDTO getReportDTO(HttpServletRequest request) {
        return  ReportDTO.builder()
                .topic(request.getParameter(TOPIC))
                .speakerId(((UserDTO) request.getSession().getAttribute(LOGGED_USER)).getId())
                .eventId(Long.parseLong(request.getParameter(EVENT_ID)))
                .build();
    }
}