package ua.java.conferences.actions.implementation.speaker;

import jakarta.servlet.http.HttpServletRequest;
import ua.java.conferences.actions.Action;
import ua.java.conferences.dto.EventDTO;
import ua.java.conferences.dto.UserDTO;
import ua.java.conferences.exceptions.*;
import ua.java.conferences.services.*;

import static ua.java.conferences.actions.ActionUtil.*;
import static ua.java.conferences.actions.constants.Pages.*;
import static ua.java.conferences.actions.constants.ParameterValues.*;
import static ua.java.conferences.actions.constants.Parameters.*;
import static ua.java.conferences.entities.role.Role.*;
import static ua.java.conferences.utils.QueryBuilderUtil.*;

public class OfferReportPageAction implements Action {

    private final EventService eventService;

    public OfferReportPageAction() {
        eventService = ServiceFactory.getInstance(DB_IMPLEMENTATION).getEventService();
    }

    @Override
    public String execute(HttpServletRequest request) throws ServiceException {
        transferAttributes(request);
        long speakerId = ((UserDTO) request.getSession().getAttribute(LOGGED_USER)).getId();
        String parameterEventId = request.getParameter(EVENT_ID);
        try {
            request.setAttribute(EVENT, getEvent(parameterEventId, speakerId));
        } catch (NoSuchEventException e) {
            request.setAttribute(ERROR, OFFER_FORBIDDEN);
        }
        return OFFER_REPORT_PAGE;
    }

    private EventDTO getEvent(String parameterEventId, long userId) throws ServiceException {
        String query = eventQueryBuilder()
                .setUserIdFilter(userId)
                .getQuery();
        return eventService.getSortedByUser(query, SPEAKER).stream()
                .filter(e -> String.valueOf(e.getId()).equals(parameterEventId))
                .findAny()
                .orElseThrow(NoSuchEventException::new);
    }

    private void transferAttributes(HttpServletRequest request) {
        transferStringFromSessionToRequest(request, MESSAGE);
        transferStringFromSessionToRequest(request, ERROR);
    }
}