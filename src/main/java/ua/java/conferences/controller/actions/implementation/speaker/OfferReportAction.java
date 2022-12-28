package ua.java.conferences.controller.actions.implementation.speaker;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.java.conferences.controller.context.AppContext;
import ua.java.conferences.controller.actions.Action;
import ua.java.conferences.dto.*;
import ua.java.conferences.exceptions.*;
import ua.java.conferences.model.services.*;
import ua.java.conferences.utils.EmailSender;

import java.time.LocalDate;

import static ua.java.conferences.controller.actions.ActionUtil.*;
import static ua.java.conferences.controller.actions.constants.ActionNames.*;
import static ua.java.conferences.controller.actions.constants.Pages.*;
import static ua.java.conferences.controller.actions.constants.ParameterValues.*;
import static ua.java.conferences.controller.actions.constants.Parameters.*;
import static ua.java.conferences.model.entities.role.Role.SPEAKER;
import static ua.java.conferences.utils.QueryBuilderUtil.*;
import static ua.java.conferences.utils.constants.Email.*;

public class OfferReportAction implements Action {
    private final ReportService reportService;
    private final EventService eventService;
    private final UserService userService;
    private final EmailSender emailSender;

    public OfferReportAction(AppContext appContext) {
        reportService = appContext.getReportService();
        eventService = appContext.getEventService();
        userService = appContext.getUserService();
        emailSender = appContext.getEmailSender();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
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
            sendEmail(reportDTO);
        } catch (IncorrectFormatException e) {
            request.getSession().setAttribute(ERROR, e.getMessage());
        }
        return getActionToRedirect(OFFER_REPORT_ACTION, EVENT_ID, String.valueOf(reportDTO.getEventId()));
    }

    private ReportDTO getReportDTO(HttpServletRequest request) {
        UserDTO speaker = (UserDTO) request.getSession().getAttribute(LOGGED_USER);
        return  ReportDTO.builder()
                .topic(request.getParameter(TOPIC))
                .speakerId(speaker.getId())
                .eventId(Long.parseLong(request.getParameter(EVENT_ID)))
                .title(request.getParameter(TITLE))
                .speakerName(speaker.getName() + " " + speaker.getSurname())
                .build();
    }

    private void sendEmail(ReportDTO report) throws ServiceException {
        for (UserDTO moderator : userService.getModerators()) {
            new Thread(
                    () -> {
                        String body = String.format(MESSAGE_OFFER_REPORT, moderator.getName(), report.getSpeakerName(),
                                report.getTopic(), report.getTitle(), report.getEventId());
                        emailSender.send(SUBJECT_NOTIFICATION, body, moderator.getEmail());})
                    .start();
        }
    }
}