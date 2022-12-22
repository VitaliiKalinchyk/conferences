package ua.java.conferences.controller.actions.implementation.visitor;

import jakarta.servlet.http.HttpServletRequest;
import ua.java.conferences.controller.context.AppContext;
import ua.java.conferences.controller.actions.Action;
import ua.java.conferences.dto.UserDTO;
import ua.java.conferences.exceptions.ServiceException;
import ua.java.conferences.model.services.*;
import ua.java.conferences.model.utils.query.QueryBuilder;

import static ua.java.conferences.controller.actions.constants.Pages.VIEW_VISITORS_EVENTS_PAGE;
import static ua.java.conferences.controller.actions.constants.Parameters.*;
import static ua.java.conferences.model.entities.role.Role.VISITOR;
import static ua.java.conferences.model.utils.PaginationUtil.*;
import static ua.java.conferences.model.utils.QueryBuilderUtil.*;

public class ViewVisitorsEventsAction implements Action {
    private final EventService eventService;

    public ViewVisitorsEventsAction(AppContext appContext) {
        eventService = appContext.getEventService();
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