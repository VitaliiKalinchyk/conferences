package ua.java.conferences.controller.actions.implementation.moderator;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.java.conferences.controller.context.AppContext;
import ua.java.conferences.controller.actions.Action;
import ua.java.conferences.dto.ReportDTO;
import ua.java.conferences.exceptions.ServiceException;
import ua.java.conferences.model.services.*;
import ua.java.conferences.utils.EmailSender;

import static ua.java.conferences.controller.actions.ActionUtil.*;
import static ua.java.conferences.controller.actions.constants.ActionNames.VIEW_REPORT_ACTION;
import static ua.java.conferences.controller.actions.constants.ParameterValues.*;
import static ua.java.conferences.controller.actions.constants.Parameters.*;
import static ua.java.conferences.utils.constants.Email.*;

public class SetOrRemoveSpeakerAction implements Action {
    private final ReportService reportService;
    private final EmailSender emailSender;

    public SetOrRemoveSpeakerAction(AppContext appContext) {
        reportService = appContext.getReportService();
        emailSender = appContext.getEmailSender();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String reportId = request.getParameter(REPORT_ID);
        setOrRemove(request, Long.parseLong(reportId));
        request.getSession().setAttribute(MESSAGE, SUCCEED_UPDATE);
        return getActionToRedirect(VIEW_REPORT_ACTION, REPORT_ID, reportId);
    }

    private void setOrRemove(HttpServletRequest request, long reportId) throws ServiceException {
        String todo = request.getParameter(TODO);
        if (todo.equals(REMOVE)) {
            reportService.deleteSpeaker(reportId);
            sendEmail(MESSAGE_REMOVE_SPEAKER, String.valueOf(reportId), request);
        } else if (todo.equals(SET)) {
            reportService.setSpeaker(reportId, Long.parseLong(request.getParameter(USER_ID)));
            sendEmail(MESSAGE_SET_SPEAKER, String.valueOf(reportId), null);
        }
    }

    private void sendEmail(String message, String reportId, HttpServletRequest request) throws ServiceException {
        ReportDTO report = reportService.getById(reportId);
        String email = request != null ? request.getParameter(EMAIL) : report.getSpeakerEmail();
        String name = request != null ? request.getParameter(NAME) : report.getSpeakerName();
        String topic = report.getTopic();
        String title = report.getTitle();
        String date = report.getDate();
        String location = report.getLocation();
        String body = String.format(message, name, topic, title, location, date);
        new Thread(() -> emailSender.send(SUBJECT_NOTIFICATION, body, email)).start();
    }
}