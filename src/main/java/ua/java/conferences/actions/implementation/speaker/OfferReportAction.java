package ua.java.conferences.actions.implementation.speaker;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.*;
import ua.java.conferences.actions.*;
import ua.java.conferences.dto.request.ReportRequestDTO;
import ua.java.conferences.dto.response.*;
import ua.java.conferences.exceptions.*;
import ua.java.conferences.services.*;

import static ua.java.conferences.actions.constants.ActionNames.*;
import static ua.java.conferences.actions.constants.Pages.*;
import static ua.java.conferences.actions.constants.ParameterValues.*;
import static ua.java.conferences.actions.constants.Parameters.*;
import static ua.java.conferences.dao.constants.DbImplementations.MYSQL;

public class OfferReportAction implements Action, ActionPost {

    private static final Logger logger = LoggerFactory.getLogger(OfferReportAction.class);

    private final EventService eventService;

    private final ReportService reportService;

    public OfferReportAction() {
        eventService = ServiceFactory.getInstance(MYSQL).getEventService();
        reportService = ServiceFactory.getInstance(MYSQL).getReportService();
    }

    @Override
    public String executeGet(HttpServletRequest request) {
        String path = OFFER_REPORT_PAGE;
        path = getPath(request, path);
        transferStringFromSessionToRequest(request, MESSAGE);
        transferStringFromSessionToRequest(request, ERROR);
        return getPath(path, request);
    }

    @Override
    public String executePost(HttpServletRequest request) {
        String path = OFFER_REPORT_PAGE;
        try {
            ReportRequestDTO report = getReport(request);
            reportService.createReport(report);
            request.getSession().setAttribute(MESSAGE, SUCCEED_ADD);
        } catch (NoSuchEventException e) {
            request.getSession().setAttribute(ERROR, OFFER_FORBIDDEN);
        } catch (IncorrectFormatException e) {
            request.getSession().setAttribute(ERROR, e.getMessage());
        } catch (ServiceException e) {
            logger.error(e.getMessage());
            path = ERROR_PAGE;
        }
        request.getSession().setAttribute(CURRENT_PATH, path);
        return getControllerDirective();
    }

    private ReportRequestDTO getReport(HttpServletRequest request) throws ServiceException {
        long userId = ((UserResponseDTO) request.getSession().getAttribute(LOGGED_USER)).getId();
        long eventId = getEventId(request.getParameter(EVENT_ID));
        String topic = request.getParameter(TOPIC);
        setParameters(request, eventId);
        checkAccess(userId, eventId);
        return new ReportRequestDTO(topic, userId, eventId);
    }

    private static void setParameters(HttpServletRequest request, long eventId) {
        String eventTitle = request.getParameter(TITLE);
        request.getSession().setAttribute(ID, eventId);
        request.getSession().setAttribute(TITLE, eventTitle);
    }

    private long getEventId(String parameterEventId) throws NoSuchEventException {
        long eventId;
        try {
            eventId = Long.parseLong(parameterEventId);
        } catch (NumberFormatException e) {
            throw new NoSuchEventException();
        }
        return eventId;
    }

    private void checkAccess(long userId, long eventId) throws ServiceException {
        boolean access = eventService.viewSpeakersEvents(userId)
                .stream().mapToLong(EventResponseDTO::getId)
                .anyMatch(e -> e == eventId);
        if (!access) {
            throw new NoSuchEventException();
        }
    }

    private  String getControllerDirective() {
        return CONTROLLER_PAGE + "?" + ACTION + "=" + OFFER_REPORT_ACTION;
    }

    private String getPath(String path, HttpServletRequest request) {
        if (path.equals(OFFER_REPORT_PAGE)) {
            String title = (String) request.getSession().getAttribute(TITLE);
            Long id = (Long) request.getSession().getAttribute(ID);
            path = path + "?" + TITLE + "=" + title +"&" + ID + "=" + id;
        }
        return path;
    }
}