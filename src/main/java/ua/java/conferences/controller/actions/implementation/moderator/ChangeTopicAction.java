package ua.java.conferences.controller.actions.implementation.moderator;

import jakarta.servlet.http.HttpServletRequest;
import ua.java.conferences.controller.context.AppContext;
import ua.java.conferences.controller.actions.Action;
import ua.java.conferences.dto.*;
import ua.java.conferences.exceptions.*;
import ua.java.conferences.model.services.*;

import static ua.java.conferences.controller.actions.ActionUtil.*;
import static ua.java.conferences.controller.actions.constants.ActionNames.VIEW_REPORT_ACTION;
import static ua.java.conferences.controller.actions.constants.ParameterValues.SUCCEED_UPDATE;
import static ua.java.conferences.controller.actions.constants.Parameters.*;

public class ChangeTopicAction implements Action {
    private final ReportService reportService;

    public ChangeTopicAction(AppContext appContext) {
        reportService = appContext.getReportService();
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