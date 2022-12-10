package ua.java.conferences.actions.implementation.speaker;

import jakarta.servlet.http.HttpServletRequest;
import ua.java.conferences.actions.*;
import ua.java.conferences.dto.request.*;
import ua.java.conferences.dto.response.*;
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
        long speakerId = ((UserResponseDTO) request.getSession().getAttribute(LOGGED_USER)).getId();
        long eventId = Long.parseLong(request.getParameter(EVENT_ID));
        String topic = request.getParameter(TOPIC);
        try {
            reportService.createReport(new ReportRequestDTO(topic, speakerId, eventId));
            request.getSession().setAttribute(MESSAGE, SUCCEED_ADD);
        } catch (IncorrectFormatException e) {
            request.getSession().setAttribute(ERROR, e.getMessage());
        }
        return getActionToRedirect(OFFER_REPORT_PAGE_ACTION, EVENT_ID, String.valueOf(eventId));
    }
}