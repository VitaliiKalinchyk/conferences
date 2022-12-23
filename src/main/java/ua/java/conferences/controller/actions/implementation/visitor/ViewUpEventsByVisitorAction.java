package ua.java.conferences.controller.actions.implementation.visitor;

import jakarta.servlet.http.HttpServletRequest;
import ua.java.conferences.controller.context.AppContext;
import ua.java.conferences.controller.actions.Action;
import ua.java.conferences.model.entities.role.Role;
import ua.java.conferences.exceptions.ServiceException;
import ua.java.conferences.model.services.*;
import ua.java.conferences.utils.query.QueryBuilder;

import static ua.java.conferences.controller.actions.constants.Pages.VIEW_UP_EVENTS_BY_VISITOR_PAGE;
import static ua.java.conferences.controller.actions.constants.ParameterValues.UPCOMING;
import static ua.java.conferences.controller.actions.constants.Parameters.*;
import static ua.java.conferences.utils.PaginationUtil.*;
import static ua.java.conferences.utils.QueryBuilderUtil.*;

public class ViewUpEventsByVisitorAction implements Action {
    private final EventService eventService;

    public ViewUpEventsByVisitorAction(AppContext appContext) {
        eventService = appContext.getEventService();
    }

    @Override
    public String execute(HttpServletRequest request) throws ServiceException {
        QueryBuilder queryBuilder = getQueryBuilder(request);
        request.setAttribute(EVENTS, eventService.getSorted(queryBuilder.getQuery()));
        int numberOfRecords = eventService.getNumberOfRecords(queryBuilder.getRecordQuery(), Role.MODERATOR);
        paginate(numberOfRecords, request);
        return VIEW_UP_EVENTS_BY_VISITOR_PAGE;
    }


    private QueryBuilder getQueryBuilder(HttpServletRequest request) {
        return eventQueryBuilder()
                .setDateFilter(UPCOMING)
                .setSortField(request.getParameter(SORT))
                .setOrder(request.getParameter(ORDER))
                .setLimits(request.getParameter(OFFSET), request.getParameter(RECORDS));
    }
}