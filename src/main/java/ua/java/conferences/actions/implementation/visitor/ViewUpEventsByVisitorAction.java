package ua.java.conferences.actions.implementation.visitor;

import jakarta.servlet.http.HttpServletRequest;
import ua.java.conferences.actions.Action;
import ua.java.conferences.entities.role.Role;
import ua.java.conferences.exceptions.ServiceException;
import ua.java.conferences.services.*;
import ua.java.conferences.utils.query.QueryBuilder;

import static ua.java.conferences.actions.ActionUtil.*;
import static ua.java.conferences.actions.constants.ParameterValues.*;
import static ua.java.conferences.actions.constants.Parameters.*;
import static ua.java.conferences.actions.constants.Pages.*;
import static ua.java.conferences.utils.PaginationUtil.*;
import static ua.java.conferences.utils.QueryBuilderUtil.*;

public class ViewUpEventsByVisitorAction implements Action {

    private final EventService eventService;

    public ViewUpEventsByVisitorAction() {
        eventService = ServiceFactory.getInstance(DB_IMPLEMENTATION).getEventService();
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