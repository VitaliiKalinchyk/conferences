package ua.java.conferences.actions.implementation.moderator;

import jakarta.servlet.http.HttpServletRequest;
import ua.java.conferences.actions.Action;
import ua.java.conferences.exceptions.*;
import ua.java.conferences.services.*;
import ua.java.conferences.utils.query.QueryBuilder;

import static ua.java.conferences.actions.ActionUtil.*;
import static ua.java.conferences.actions.constants.Pages.*;
import static ua.java.conferences.actions.constants.Parameters.*;
import static ua.java.conferences.entities.role.Role.*;
import static ua.java.conferences.utils.PaginationUtil.*;
import static ua.java.conferences.utils.QueryBuilderUtil.*;

public class ViewEventsAction implements Action {
    private final EventService eventService;

    public ViewEventsAction() {
        eventService = ServiceFactory.getInstance(DB_IMPLEMENTATION).getEventService();
    }

    @Override
    public String execute(HttpServletRequest request) throws ServiceException {
        QueryBuilder queryBuilder = getQueryBuilder(request);
        request.setAttribute(EVENTS, eventService.getSorted(queryBuilder.getQuery()));
        int numberOfRecords = eventService.getNumberOfRecords(queryBuilder.getRecordQuery(), MODERATOR);
        paginate(numberOfRecords, request);
        return VIEW_EVENTS_PAGE;
    }

    private QueryBuilder getQueryBuilder(HttpServletRequest request) {
        return eventQueryBuilder()
                .setDateFilter(request.getParameter(DATE))
                .setSortField(request.getParameter(SORT))
                .setOrder(request.getParameter(ORDER))
                .setLimits(request.getParameter(OFFSET), request.getParameter(RECORDS));
    }
}