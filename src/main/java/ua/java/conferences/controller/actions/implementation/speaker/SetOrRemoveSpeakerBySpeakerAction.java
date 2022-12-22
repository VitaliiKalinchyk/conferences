package ua.java.conferences.controller.actions.implementation.speaker;

import jakarta.servlet.http.HttpServletRequest;
import ua.java.conferences.controller.context.AppContext;
import ua.java.conferences.controller.actions.Action;
import ua.java.conferences.dto.UserDTO;
import ua.java.conferences.exceptions.*;
import ua.java.conferences.model.services.*;

import static ua.java.conferences.controller.actions.ActionUtil.*;
import static ua.java.conferences.controller.actions.constants.ActionNames.*;
import static ua.java.conferences.controller.actions.constants.ParameterValues.*;
import static ua.java.conferences.controller.actions.constants.Parameters.*;

public class SetOrRemoveSpeakerBySpeakerAction implements Action {
    private final ReportService reportService;

    public SetOrRemoveSpeakerBySpeakerAction(AppContext appContext) {
        reportService = appContext.getReportService();
    }

    @Override
    public String execute(HttpServletRequest request) throws ServiceException {
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
        } else if (todo.equals(REMOVE)) {
            reportService.deleteSpeaker(reportId);
        }
    }
}