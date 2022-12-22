package ua.java.conferences.controller.actions.implementation.moderator;

import jakarta.servlet.http.HttpServletRequest;
import ua.java.conferences.controller.context.AppContext;
import ua.java.conferences.controller.actions.Action;
import ua.java.conferences.dto.*;
import ua.java.conferences.exceptions.*;
import ua.java.conferences.model.services.*;

import java.time.LocalDate;

import static ua.java.conferences.controller.actions.ActionUtil.*;
import static ua.java.conferences.controller.actions.constants.Pages.VIEW_REPORT_PAGE;
import static ua.java.conferences.controller.actions.constants.Parameters.*;

public class ViewReportAction implements Action {
    private final ReportService reportService;
    private final UserService userService;

    public ViewReportAction(AppContext appContext) {
        reportService = appContext.getReportService();
        userService = appContext.getUserService();
    }

    @Override
    public String execute(HttpServletRequest request) throws ServiceException {
        transferAttributes(request);
        try {
            ReportDTO report = reportService.getById(request.getParameter(REPORT_ID));
            request.setAttribute(IS_COMING, isFutureReport(report));
            request.setAttribute(REPORT, report);
            request.setAttribute(SPEAKERS, userService.getSpeakers());
        } catch (NoSuchReportException e) {
            request.setAttribute(ERROR, e.getMessage());
        }
        return VIEW_REPORT_PAGE;
    }

    private static boolean isFutureReport(ReportDTO report) {
        return LocalDate.now().isBefore(LocalDate.parse(report.getDate()));
    }

    private void transferAttributes(HttpServletRequest request) {
        transferStringFromSessionToRequest(request, MESSAGE);
        transferStringFromSessionToRequest(request, ERROR);
    }
}