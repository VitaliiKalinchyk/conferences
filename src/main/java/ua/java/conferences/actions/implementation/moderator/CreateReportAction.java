package ua.java.conferences.actions.implementation.moderator;

import jakarta.servlet.http.HttpServletRequest;
import ua.java.conferences.actions.Action;
import ua.java.conferences.dto.*;
import ua.java.conferences.exceptions.*;
import ua.java.conferences.services.*;

import static ua.java.conferences.actions.ActionUtil.*;
import static ua.java.conferences.actions.constants.ActionNames.*;
import static ua.java.conferences.actions.constants.Parameters.*;

public class CreateReportAction implements Action {
    private final ReportService reportService;

    public CreateReportAction() {
        reportService = ServiceFactory.getInstance(DB_IMPLEMENTATION).getReportService();
    }

    @Override
    public String execute(HttpServletRequest request) throws ServiceException {
        ReportDTO report = getReportDTO(request);
        try {
            reportService.addReport(report);
        } catch (IncorrectFormatException e) {
            request.getSession().setAttribute(ERROR, e.getMessage());
        }
        return getActionToRedirect(SEARCH_EVENT_ACTION, EVENT_ID, String.valueOf(report.getEventId()));
    }

    private ReportDTO getReportDTO(HttpServletRequest request) {
        return ReportDTO.builder()
                .topic(request.getParameter(TOPIC))
                .eventId(Long.parseLong(request.getParameter(EVENT_ID)))
                .build();
    }
}