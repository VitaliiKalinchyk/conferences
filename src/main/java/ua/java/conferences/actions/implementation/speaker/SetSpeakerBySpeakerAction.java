package ua.java.conferences.actions.implementation.speaker;

import jakarta.servlet.http.HttpServletRequest;
import ua.java.conferences.actions.Action;
import ua.java.conferences.dto.UserDTO;
import ua.java.conferences.exceptions.*;
import ua.java.conferences.services.*;

import static ua.java.conferences.actions.ActionUtil.*;
import static ua.java.conferences.actions.constants.ActionNames.*;
import static ua.java.conferences.actions.constants.Parameters.*;

public class SetSpeakerBySpeakerAction implements Action {

    private final ReportService reportService;

    public SetSpeakerBySpeakerAction() {
        reportService = ServiceFactory.getInstance(DB_IMPLEMENTATION).getReportService();
    }

    @Override
    public String execute(HttpServletRequest request) throws ServiceException {
        long speakerId = ((UserDTO) request.getSession().getAttribute(LOGGED_USER)).getId();
        long reportId = Long.parseLong(request.getParameter(REPORT_ID));
        String eventId = request.getParameter(EVENT_ID);
        reportService.setSpeaker(reportId, speakerId);
        return getActionToRedirect(VIEW_EVENT_BY_SPEAKER_ACTION, EVENT_ID, String.valueOf(eventId));
    }
}