package ua.java.conferences.controller.actions.implementation.speaker;

import jakarta.servlet.http.HttpServletRequest;
import ua.java.conferences.controller.context.AppContext;
import ua.java.conferences.controller.actions.Action;
import ua.java.conferences.dto.UserDTO;
import ua.java.conferences.exceptions.ServiceException;
import ua.java.conferences.model.services.*;

import static ua.java.conferences.controller.actions.constants.Pages.*;
import static ua.java.conferences.controller.actions.constants.Parameters.*;

public class ViewSpeakersReportsAction implements Action {
    private final ReportService reportService;

    public ViewSpeakersReportsAction(AppContext appContext) {
        reportService = appContext.getReportService();
    }

    @Override
    public String execute(HttpServletRequest request) throws ServiceException {
        long speakerId = ((UserDTO) request.getSession().getAttribute(LOGGED_USER)).getId();
        request.setAttribute(REPORTS, reportService.viewSpeakersReports(speakerId));
        return VIEW_SPEAKERS_REPORTS_PAGE;
    }
}