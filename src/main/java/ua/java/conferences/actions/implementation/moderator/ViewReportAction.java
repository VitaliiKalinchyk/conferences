package ua.java.conferences.actions.implementation.moderator;

import jakarta.servlet.http.HttpServletRequest;
import ua.java.conferences.actions.Action;
import ua.java.conferences.dto.*;
import ua.java.conferences.exceptions.*;
import ua.java.conferences.services.*;

import java.time.LocalDate;

import static ua.java.conferences.actions.ActionUtil.*;
import static ua.java.conferences.actions.constants.Pages.*;
import static ua.java.conferences.actions.constants.Parameters.*;

public class ViewReportAction implements Action {
    private final ReportService reportService;
    private final UserService userService;

    public ViewReportAction() {
        reportService = ServiceFactory.getInstance(DB_IMPLEMENTATION).getReportService();
        userService = ServiceFactory.getInstance(DB_IMPLEMENTATION).getUserService();
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