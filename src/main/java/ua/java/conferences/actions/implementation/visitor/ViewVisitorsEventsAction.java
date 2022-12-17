package ua.java.conferences.actions.implementation.visitor;

import jakarta.servlet.http.HttpServletRequest;
import ua.java.conferences.actions.Action;
import ua.java.conferences.dto.UserDTO;
import ua.java.conferences.exceptions.ServiceException;
import ua.java.conferences.services.*;

import static ua.java.conferences.actions.ActionUtil.*;
import static ua.java.conferences.actions.constants.Pages.*;
import static ua.java.conferences.actions.constants.ParameterValues.*;
import static ua.java.conferences.actions.constants.Parameters.*;
import static ua.java.conferences.entities.role.Role.*;
import static ua.java.conferences.utils.QueryBuilderUtil.*;

public class ViewVisitorsEventsAction implements Action {

    private final EventService eventService;

    public ViewVisitorsEventsAction() {
        eventService = ServiceFactory.getInstance(DB_IMPLEMENTATION).getEventService();
    }

    @Override
    public String execute(HttpServletRequest request) throws ServiceException {
        long userId = ((UserDTO) request.getSession().getAttribute(LOGGED_USER)).getId();
        String past = request.getParameter(PAST);
        request.setAttribute(EVENTS, eventService.getSortedByUser(getQuery(userId, past), VISITOR));
        return VIEW_VISITORS_EVENTS_PAGE;
    }

    private static String getQuery(long userId, String past) {
        return visitorEventQueryBuilder()
                .setIdFilter(userId)
                .setDateFilter(past.equals(PAST) ? PASSED : UPCOMING)
                .getQuery();
    }
}