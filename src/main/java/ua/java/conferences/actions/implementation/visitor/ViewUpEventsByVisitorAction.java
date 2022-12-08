package ua.java.conferences.actions.implementation.visitor;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.*;
import ua.java.conferences.actions.Action;
import ua.java.conferences.dto.response.EventResponseDTO;
import ua.java.conferences.exceptions.ServiceException;
import ua.java.conferences.services.*;

import java.util.List;

import static ua.java.conferences.actions.constants.ParameterValues.*;
import static ua.java.conferences.actions.constants.Parameters.*;
import static ua.java.conferences.actions.constants.Pages.*;
import static ua.java.conferences.dao.constants.DbImplementations.MYSQL;

public class ViewUpEventsByVisitorAction implements Action {

    private static final Logger logger = LoggerFactory.getLogger(ViewUpEventsByVisitorAction.class);

    private final EventService eventService;

    public ViewUpEventsByVisitorAction() {
        eventService = ServiceFactory.getInstance(MYSQL).getEventService();
    }

    @Override
    public String executeGet(HttpServletRequest request) {
        String path = VIEW_UP_EVENTS_BY_VISITOR_PAGE;
        String sortedField = getSortedField(request);
        String order = getOrder(request);
        try {
            List<EventResponseDTO> events = eventService.viewSortedEventsByUser(sortedField, order);
            request.setAttribute(EVENTS, events);
        } catch (ServiceException e) {
            logger.error(e.getMessage());
            path = ERROR_PAGE;
        }
        return path;
    }

    private String getSortedField(HttpServletRequest request) {
        String parameter = request.getParameter(SORT_FIELD);
        return parameter != null ? parameter : ID;
    }

    private String getOrder(HttpServletRequest request) {
        String parameter = request.getParameter(SORT_ORDER);
        return parameter != null ? parameter : ASCENDING_ORDER;
    }
}