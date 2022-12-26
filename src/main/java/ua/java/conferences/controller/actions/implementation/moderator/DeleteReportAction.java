package ua.java.conferences.controller.actions.implementation.moderator;

import jakarta.servlet.http.HttpServletRequest;
import ua.java.conferences.controller.context.AppContext;
import ua.java.conferences.controller.actions.Action;
import ua.java.conferences.exceptions.ServiceException;
import ua.java.conferences.model.services.*;
import ua.java.conferences.utils.EmailSender;

import static ua.java.conferences.controller.actions.ActionUtil.*;
import static ua.java.conferences.controller.actions.constants.ActionNames.SEARCH_EVENT_ACTION;
import static ua.java.conferences.controller.actions.constants.ParameterValues.SUCCEED_DELETE;
import static ua.java.conferences.controller.actions.constants.Parameters.*;
import static ua.java.conferences.utils.constants.Email.*;

public class DeleteReportAction implements Action {
    private final ReportService reportService;
    private final EmailSender emailSender;

    public DeleteReportAction(AppContext appContext) {
        reportService = appContext.getReportService();
        emailSender = appContext.getEmailSender();
    }

    @Override
    public String execute(HttpServletRequest request) throws ServiceException {
        reportService.delete(request.getParameter(REPORT_ID));
        request.getSession().setAttribute(MESSAGE, SUCCEED_DELETE);
        sendEmail(request);
        return getActionToRedirect(SEARCH_EVENT_ACTION, EVENT_ID, request.getParameter(EVENT_ID));
    }

    private void sendEmail(HttpServletRequest request) {
        String email = request.getParameter(EMAIL);
        if (email != null) {
            String topic = request.getParameter(TOPIC);
            String title = request.getParameter(TITLE);
            String date = request.getParameter(DATE);
            String location = request.getParameter(LOCATION);
            String name = request.getParameter(NAME);
            String body = String.format(MESSAGE_REPORT_DELETED, name, topic, title, location, date);
            new Thread(() -> emailSender.send(SUBJECT_NOTIFICATION, body, email)).start();
        }
    }
}