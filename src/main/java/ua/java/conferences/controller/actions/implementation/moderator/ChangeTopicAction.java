package ua.java.conferences.controller.actions.implementation.moderator;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.java.conferences.controller.context.AppContext;
import ua.java.conferences.controller.actions.Action;
import ua.java.conferences.dto.*;
import ua.java.conferences.exceptions.*;
import ua.java.conferences.model.services.*;
import ua.java.conferences.utils.EmailSender;

import static ua.java.conferences.controller.actions.ActionUtil.*;
import static ua.java.conferences.controller.actions.constants.ActionNames.VIEW_REPORT_ACTION;
import static ua.java.conferences.controller.actions.constants.ParameterValues.SUCCEED_UPDATE;
import static ua.java.conferences.controller.actions.constants.Parameters.*;
import static ua.java.conferences.utils.constants.Email.*;

public class ChangeTopicAction implements Action {
    private final ReportService reportService;
    private final EmailSender emailSender;

    public ChangeTopicAction(AppContext appContext) {
        reportService = appContext.getReportService();
        emailSender = appContext.getEmailSender();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        ReportDTO report = getReportDTO(request);
        try {
            reportService.update(report);
            request.getSession().setAttribute(MESSAGE, SUCCEED_UPDATE);
            sendEmail(report, request);
        } catch (IncorrectFormatException e) {
            request.getSession().setAttribute(ERROR, e.getMessage());
        }
        return getActionToRedirect(VIEW_REPORT_ACTION, REPORT_ID, String.valueOf(report.getId()));
    }

    private void sendEmail(ReportDTO reportDTO, HttpServletRequest request) throws ServiceException {
        ReportDTO report = reportService.getById(String.valueOf(reportDTO.getId()));
        String body = String.format(MESSAGE_TOPIC_CHANGED, report.getSpeakerName(), report.getTitle(),
                report.getLocation(), report.getDate(), report.getTopic(), getURL(request), report.getEventId());
        new Thread(() -> emailSender.send(SUBJECT_NOTIFICATION, body, report.getSpeakerEmail())).start();
    }

    private ReportDTO getReportDTO(HttpServletRequest request) {
        return ReportDTO.builder()
                .id(Long.parseLong(request.getParameter(REPORT_ID)))
                .topic(request.getParameter(TOPIC))
                .build();
    }
}