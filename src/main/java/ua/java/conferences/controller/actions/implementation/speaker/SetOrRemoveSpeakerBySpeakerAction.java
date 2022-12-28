package ua.java.conferences.controller.actions.implementation.speaker;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.java.conferences.controller.context.AppContext;
import ua.java.conferences.controller.actions.Action;
import ua.java.conferences.dto.UserDTO;
import ua.java.conferences.exceptions.*;
import ua.java.conferences.model.services.*;
import ua.java.conferences.utils.EmailSender;

import static ua.java.conferences.controller.actions.ActionUtil.*;
import static ua.java.conferences.controller.actions.constants.ActionNames.*;
import static ua.java.conferences.controller.actions.constants.ParameterValues.*;
import static ua.java.conferences.controller.actions.constants.Parameters.*;
import static ua.java.conferences.utils.constants.Email.*;

public class SetOrRemoveSpeakerBySpeakerAction implements Action {
    private final ReportService reportService;
    private final UserService userService;
    private final EmailSender emailSender;

    public SetOrRemoveSpeakerBySpeakerAction(AppContext appContext) {
        reportService = appContext.getReportService();
        userService = appContext.getUserService();
        emailSender = appContext.getEmailSender();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        long reportId = Long.parseLong(request.getParameter(REPORT_ID));
        String eventId = request.getParameter(EVENT_ID);
        setOrRemove(request, reportId);
        return eventId != null ? getActionToRedirect(VIEW_EVENT_BY_SPEAKER_ACTION, EVENT_ID, eventId)
                : getActionToRedirect(VIEW_SPEAKERS_REPORTS_ACTION);
    }

    private void setOrRemove(HttpServletRequest request, long reportId) throws ServiceException {
        String todo = request.getParameter(TODO);
        if(todo.equals(SET)) {
            long speakerId = ((UserDTO) request.getSession().getAttribute(LOGGED_USER)).getId();
            reportService.setSpeaker(reportId, speakerId);
            sendEmail(request, MESSAGE_SET_SPEAKER_BY_SPEAKER);
        } else if (todo.equals(REMOVE)) {
            reportService.deleteSpeaker(reportId);
            sendEmail(request, MESSAGE_REMOVE_SPEAKER_BY_SPEAKER);
        }
    }

    private void sendEmail(HttpServletRequest request, String message) throws ServiceException {
        UserDTO speaker = (UserDTO) request.getSession().getAttribute(LOGGED_USER);
        String speakerName = speaker.getName() + " " + speaker.getSurname();
        String topic = request.getParameter(TOPIC);
        String title = request.getParameter(TITLE);
        String reportId = request.getParameter(REPORT_ID);
        for (UserDTO moderator : userService.getModerators()) {
            new Thread(
                    () -> {
                        String body = String.format(message, moderator.getName(), speakerName, topic, title, reportId);
                        emailSender.send(SUBJECT_NOTIFICATION, body, moderator.getEmail());})
                    .start();
        }
    }
}