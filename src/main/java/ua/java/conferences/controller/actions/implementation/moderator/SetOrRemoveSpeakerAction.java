package ua.java.conferences.controller.actions.implementation.moderator;

import jakarta.servlet.http.HttpServletRequest;
import ua.java.conferences.controller.context.AppContext;
import ua.java.conferences.controller.actions.Action;
import ua.java.conferences.exceptions.ServiceException;
import ua.java.conferences.model.services.*;

import static ua.java.conferences.controller.actions.ActionUtil.*;
import static ua.java.conferences.controller.actions.constants.ActionNames.VIEW_REPORT_ACTION;
import static ua.java.conferences.controller.actions.constants.ParameterValues.*;
import static ua.java.conferences.controller.actions.constants.Parameters.*;

public class SetOrRemoveSpeakerAction implements Action {
    private final ReportService reportService;

    public SetOrRemoveSpeakerAction(AppContext appContext) {
        reportService = appContext.getReportService();
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