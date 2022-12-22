package ua.java.conferences.controller.actions.implementation.moderator;

import jakarta.servlet.http.HttpServletRequest;
import ua.java.conferences.controller.context.AppContext;
import ua.java.conferences.controller.actions.Action;
import ua.java.conferences.exceptions.ServiceException;
import ua.java.conferences.model.services.*;

import static ua.java.conferences.controller.actions.ActionUtil.*;
import static ua.java.conferences.controller.actions.constants.ActionNames.SEARCH_EVENT_ACTION;
import static ua.java.conferences.controller.actions.constants.ParameterValues.SUCCEED_DELETE;
import static ua.java.conferences.controller.actions.constants.Parameters.*;

public class DeleteReportAction implements Action {
    private final ReportService reportService;

    public DeleteReportAction(AppContext appContext) {
        reportService = appContext.getReportService();
    }

    @Override
    public String execute(HttpServletRequest request) throws ServiceException {
        reportService.delete(request.getParameter(REPORT_ID));
        request.getSession().setAttribute(MESSAGE, SUCCEED_DELETE);
        return getActionToRedirect(SEARCH_EVENT_ACTION, EVENT_ID, request.getParameter(EVENT_ID));
    }
}