package ua.java.conferences.actions.implementation.visitor;

import jakarta.servlet.http.HttpServletRequest;
import ua.java.conferences.actions.Action;
import ua.java.conferences.dto.UserDTO;
import ua.java.conferences.exceptions.ServiceException;
import ua.java.conferences.services.*;
import ua.java.conferences.utils.query.QueryBuilder;

import static ua.java.conferences.actions.ActionUtil.*;
import static ua.java.conferences.actions.constants.Pages.*;
import static ua.java.conferences.actions.constants.Parameters.*;
import static ua.java.conferences.entities.role.Role.*;
import static ua.java.conferences.utils.PaginationUtil.*;
import static ua.java.conferences.utils.QueryBuilderUtil.*;

public class ViewVisitorsEventsAction implements Action {

    private final EventService eventService;

    public ViewVisitorsEventsAction() {
        eventService = ServiceFactory.getInstance(DB_IMPLEMENTATION).getEventService();
    }

    @Override
    public String execute(HttpServletRequest request) throws ServiceException {
        QueryBuilder queryBuilder = getQueryBuilder(request);
        request.setAttribute(EVENTS, eventService.getSortedByUser(queryBuilder.getQuery(), VISITOR));
        int numberOfRecords = eventService.getNumberOfRecords(queryBuilder.getRecordQuery(), VISITOR);
        paginate(numberOfRecords, request);
        return VIEW_VISITORS_EVENTS_PAGE;
    }

    private QueryBuilder getQueryBuilder(HttpServletRequest request) {
        return visitorEventQueryBuilder()
                .setUserIdFilter(((UserDTO) request.getSession().getAttribute(LOGGED_USER)).getId())
                .setDateFilter(request.getParameter(DATE))
                .setSortField(request.getParameter(SORT))
                .setOrder(request.getParameter(ORDER))
                .setLimits(request.getParameter(OFFSET), request.getParameter(RECORDS));
    }
}