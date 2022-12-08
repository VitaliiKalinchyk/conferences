package ua.java.conferences.actions.implementation.visitor;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.*;
import ua.java.conferences.actions.Action;
import ua.java.conferences.actions.constants.Parameters;
import ua.java.conferences.dto.response.*;
import ua.java.conferences.exceptions.ServiceException;
import ua.java.conferences.services.*;

import java.util.List;

import static ua.java.conferences.actions.constants.Pages.*;
import static ua.java.conferences.dao.constants.DbImplementations.MYSQL;

public class ViewVisitorsEventsAction implements Action {

    private static final Logger logger = LoggerFactory.getLogger(ViewVisitorsEventsAction.class);

    private final EventService eventService;

    public ViewVisitorsEventsAction() {
        eventService = ServiceFactory.getInstance(MYSQL).getEventService();
    }

    @Override
    public String executeGet(HttpServletRequest request) {
        String path = VIEW_VISITORS_EVENTS_PAGE;
        long userId = ((UserResponseDTO) request.getSession().getAttribute(Parameters.LOGGED_USER)).getId();
        try {
            List<EventResponseDTO> events = eventService.viewUsersEvents(userId);
            request.setAttribute(Parameters.EVENTS, events);
        } catch (ServiceException e) {
            logger.error(e.getMessage());
            path = ERROR_PAGE;
        }
        return path;
    }
}