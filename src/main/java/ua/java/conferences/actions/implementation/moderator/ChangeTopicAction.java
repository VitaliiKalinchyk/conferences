package ua.java.conferences.actions.implementation.moderator;

import jakarta.servlet.http.HttpServletRequest;
import ua.java.conferences.actions.Action;
import ua.java.conferences.dto.*;
import ua.java.conferences.exceptions.*;
import ua.java.conferences.services.*;

import static ua.java.conferences.actions.ActionUtil.*;
import static ua.java.conferences.actions.constants.ActionNames.*;
import static ua.java.conferences.actions.constants.ParameterValues.*;
import static ua.java.conferences.actions.constants.Parameters.*;

public class ChangeTopicAction implements Action {
    private final ReportService reportService;

    public ChangeTopicAction() {
        reportService = ServiceFactory.getInstance(DB_IMPLEMENTATION).getReportService();
    }

    @Override
    public String execute(HttpServletRequest request) throws ServiceException {
        ReportDTO report = getReportDTO(request);
        try {
            reportService.update(report);
            request.getSession().setAttribute(MESSAGE, SUCCEED_UPDATE);
        } catch (IncorrectFormatException e) {
            request.getSession().setAttribute(ERROR, e.getMessage());
        }
        return getActionToRedirect(VIEW_REPORT_ACTION, REPORT_ID, String.valueOf(report.getId()));
    }

    private ReportDTO getReportDTO(HttpServletRequest request) {
        return ReportDTO.builder()
                .id(Long.parseLong(request.getParameter(REPORT_ID)))
                .topic(request.getParameter(TOPIC))
                .build();
    }
}