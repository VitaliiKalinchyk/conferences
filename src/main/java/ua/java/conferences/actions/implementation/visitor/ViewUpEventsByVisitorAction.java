package ua.java.conferences.actions.implementation.visitor;

import jakarta.servlet.http.HttpServletRequest;
import ua.java.conferences.actions.Action;
import ua.java.conferences.exceptions.ServiceException;
import ua.java.conferences.services.*;

import static ua.java.conferences.actions.ActionUtil.*;
import static ua.java.conferences.actions.constants.ParameterValues.*;
import static ua.java.conferences.actions.constants.Parameters.*;
import static ua.java.conferences.actions.constants.Pages.*;

public class ViewUpEventsByVisitorAction implements Action {

    private final EventService eventService;

    public ViewUpEventsByVisitorAction() {
        eventService = ServiceFactory.getInstance(DB_IMPLEMENTATION).getEventService();
    }

    @Override
    public String execute(HttpServletRequest request) throws ServiceException {
        String sortedField = getSortedField(request);
        String order = getOrder(request);
        request.setAttribute(EVENTS, eventService.viewSortedEventsByUser(sortedField, order));
        return VIEW_UP_EVENTS_BY_VISITOR_PAGE;
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