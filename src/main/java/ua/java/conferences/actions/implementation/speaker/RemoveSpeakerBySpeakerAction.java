package ua.java.conferences.actions.implementation.speaker;

import jakarta.servlet.http.HttpServletRequest;
import ua.java.conferences.actions.Action;
import ua.java.conferences.exceptions.ServiceException;
import ua.java.conferences.services.*;

import static ua.java.conferences.actions.ActionUtil.*;
import static ua.java.conferences.actions.constants.ActionNames.*;
import static ua.java.conferences.actions.constants.Parameters.*;

public class RemoveSpeakerBySpeakerAction implements Action {

    private final ReportService reportService;

    public RemoveSpeakerBySpeakerAction() {
        reportService = ServiceFactory.getInstance(DB_IMPLEMENTATION).getReportService();
    }

    @Override
    public String execute(HttpServletRequest request) throws ServiceException {
        long reportId = Long.parseLong(request.getParameter(REPORT_ID));
        String eventId = request.getParameter(EVENT_ID);
        reportService.deleteSpeaker(reportId);
        return eventId != null ? getActionToRedirect(VIEW_EVENT_BY_SPEAKER_ACTION, EVENT_ID, eventId)
                : getActionToRedirect(VIEW_SPEAKERS_REPORTS_ACTION);
    }
}