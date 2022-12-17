package ua.java.conferences.actions.implementation.speaker;

import jakarta.servlet.http.HttpServletRequest;
import ua.java.conferences.actions.*;
import ua.java.conferences.dto.*;
import ua.java.conferences.exceptions.*;
import ua.java.conferences.services.*;

import static ua.java.conferences.actions.ActionUtil.*;
import static ua.java.conferences.actions.constants.ActionNames.*;
import static ua.java.conferences.actions.constants.ParameterValues.*;
import static ua.java.conferences.actions.constants.Parameters.*;

public class OfferReportAction implements Action {

    private final ReportService reportService;

    public OfferReportAction() {
        reportService = ServiceFactory.getInstance(DB_IMPLEMENTATION).getReportService();
    }

    @Override
    public String execute(HttpServletRequest request) throws ServiceException {
        ReportDTO reportDTO = getReportDTO(request);
        try {
            reportService.addReport(reportDTO);
            request.getSession().setAttribute(MESSAGE, SUCCEED_ADD);
        } catch (IncorrectFormatException e) {
            request.getSession().setAttribute(ERROR, e.getMessage());
        }
        return getActionToRedirect(OFFER_REPORT_PAGE_ACTION, EVENT_ID, String.valueOf(reportDTO.getEventId()));
    }

    private ReportDTO getReportDTO(HttpServletRequest request) {
        return  ReportDTO.builder()
                .topic(request.getParameter(TOPIC))
                .speakerId(((UserDTO) request.getSession().getAttribute(LOGGED_USER)).getId())
                .eventId(Long.parseLong(request.getParameter(EVENT_ID)))
                .build();
    }
}