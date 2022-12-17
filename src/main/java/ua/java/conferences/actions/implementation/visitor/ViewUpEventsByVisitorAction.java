package ua.java.conferences.actions.implementation.visitor;

import jakarta.servlet.http.HttpServletRequest;
import ua.java.conferences.actions.Action;
import ua.java.conferences.exceptions.ServiceException;
import ua.java.conferences.services.*;

import static ua.java.conferences.actions.ActionUtil.*;
import static ua.java.conferences.actions.constants.ParameterValues.*;
import static ua.java.conferences.actions.constants.Parameters.*;
import static ua.java.conferences.actions.constants.Pages.*;
import static ua.java.conferences.utils.QueryBuilderUtil.*;

public class ViewUpEventsByVisitorAction implements Action {

    private final EventService eventService;

    public ViewUpEventsByVisitorAction() {
        eventService = ServiceFactory.getInstance(DB_IMPLEMENTATION).getEventService();
    }

    @Override
    public String execute(HttpServletRequest request) throws ServiceException {
        request.setAttribute(EVENTS, eventService
                .getSorted(getQuery()));
        return VIEW_UP_EVENTS_BY_VISITOR_PAGE;
    }

    private static String getQuery() {
        return eventQueryBuilder()
                .setDateFilter(UPCOMING)
                .getQuery();
    }
}