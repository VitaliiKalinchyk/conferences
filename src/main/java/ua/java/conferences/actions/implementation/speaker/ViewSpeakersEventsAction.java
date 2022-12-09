package ua.java.conferences.actions.implementation.speaker;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.*;
import ua.java.conferences.actions.Action;
import ua.java.conferences.dto.response.*;
import ua.java.conferences.exceptions.ServiceException;
import ua.java.conferences.services.*;

import java.util.List;

import static ua.java.conferences.actions.constants.Pages.*;
import static ua.java.conferences.actions.constants.Parameters.*;
import static ua.java.conferences.dao.constants.DbImplementations.MYSQL;

public class ViewSpeakersEventsAction implements Action {

    private static final Logger logger = LoggerFactory.getLogger(ViewSpeakersEventsAction.class);

    private final EventService eventService;

    public ViewSpeakersEventsAction() {
        eventService = ServiceFactory.getInstance(MYSQL).getEventService();
    }

    @Override
    public String executeGet(HttpServletRequest request) {
        String path = VIEW_SPEAKERS_EVENTS_PAGE;
        long userId = ((UserResponseDTO) request.getSession().getAttribute(LOGGED_USER)).getId();
        String passed = request.getParameter(PASSED);
        try {
            List<EventResponseDTO> events;
            if (passed != null && passed.equals(PASSED)) {
                events = eventService.viewSpeakersPastEvents(userId);
            } else {
                events = eventService.viewSpeakersEvents(userId);
            }
            request.setAttribute(EVENTS, events);
        } catch (ServiceException e) {
            logger.error(e.getMessage());
            path = ERROR_PAGE;
        }
        return path;
    }
}