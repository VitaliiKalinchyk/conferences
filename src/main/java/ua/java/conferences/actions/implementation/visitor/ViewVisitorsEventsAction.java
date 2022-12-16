package ua.java.conferences.actions.implementation.visitor;

import jakarta.servlet.http.HttpServletRequest;
import ua.java.conferences.actions.Action;
import ua.java.conferences.dto.UserDTO;
import ua.java.conferences.entities.role.Role;
import ua.java.conferences.exceptions.ServiceException;
import ua.java.conferences.services.*;
import ua.java.conferences.utils.sorting.Sorting;

import static ua.java.conferences.actions.ActionUtil.*;
import static ua.java.conferences.actions.constants.Pages.*;
import static ua.java.conferences.actions.constants.ParameterValues.*;
import static ua.java.conferences.actions.constants.Parameters.*;

public class ViewVisitorsEventsAction implements Action {

    private final EventService eventService;

    public ViewVisitorsEventsAction() {
        eventService = ServiceFactory.getInstance(DB_IMPLEMENTATION).getEventService();
    }

    @Override
    public String execute(HttpServletRequest request) throws ServiceException {
        long userId = ((UserDTO) request.getSession().getAttribute(LOGGED_USER)).getId();
        Sorting sorting;
        String past = request.getParameter(PAST);
        if (past != null && past.equals(PAST)) {
            sorting = Sorting.getEventSorting(PASSED, ID, ASCENDING_ORDER);
        } else {
            sorting = Sorting.getEventSorting(UPCOMING, ID, ASCENDING_ORDER);
        }
        request.setAttribute(EVENTS, eventService
                .getSortedByUser(userId, sorting, "0", String.valueOf(Integer.MAX_VALUE), Role.VISITOR.name()));
        return VIEW_VISITORS_EVENTS_PAGE;
    }
}