package ua.java.conferences.actions.implementation.moderator;

import jakarta.servlet.http.HttpServletRequest;
import ua.java.conferences.actions.Action;
import ua.java.conferences.exceptions.ServiceException;
import ua.java.conferences.services.*;

import static ua.java.conferences.actions.ActionUtil.*;
import static ua.java.conferences.actions.constants.ActionNames.*;
import static ua.java.conferences.actions.constants.ParameterValues.*;
import static ua.java.conferences.actions.constants.Parameters.*;

public class SetOrRemoveSpeakerAction implements Action {

    private final ReportService reportService;

    public SetOrRemoveSpeakerAction() {
        reportService = ServiceFactory.getInstance(DB_IMPLEMENTATION).getReportService();
    }

    @Override
    public String execute(HttpServletRequest request) throws ServiceException {
        String reportId = request.getParameter(REPORT_ID);
        setOrRemove(request, Long.parseLong(reportId));
        request.getSession().setAttribute(MESSAGE, SUCCEED_UPDATE);
        return getActionToRedirect(VIEW_REPORT_ACTION, REPORT_ID, reportId);
    }

    private void setOrRemove(HttpServletRequest request, long reportId) throws ServiceException {
        String todo = request.getParameter(TODO);
        if (todo.equals(REMOVE)) {
            reportService.deleteSpeaker(reportId);
        } else if (todo.equals(SET)) {
            reportService.setSpeaker(reportId, Long.parseLong(request.getParameter(USER_ID)));
        }
    }
}