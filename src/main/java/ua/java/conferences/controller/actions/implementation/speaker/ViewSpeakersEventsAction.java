package ua.java.conferences.controller.actions.implementation.speaker;

import jakarta.servlet.http.HttpServletRequest;
import ua.java.conferences.controller.context.AppContext;
import ua.java.conferences.controller.actions.Action;
import ua.java.conferences.dto.*;
import ua.java.conferences.exceptions.ServiceException;
import ua.java.conferences.model.services.*;
import ua.java.conferences.model.utils.query.QueryBuilder;

import static ua.java.conferences.controller.actions.constants.Pages.VIEW_SPEAKERS_EVENTS_PAGE;
import static ua.java.conferences.controller.actions.constants.Parameters.*;
import static ua.java.conferences.model.entities.role.Role.SPEAKER;
import static ua.java.conferences.model.utils.PaginationUtil.paginate;
import static ua.java.conferences.model.utils.QueryBuilderUtil.*;

public class ViewSpeakersEventsAction implements Action {
    private final EventService eventService;

    public ViewSpeakersEventsAction(AppContext appContext) {
        eventService = appContext.getEventService();
    }

    @Override
    public String execute(HttpServletRequest request) throws ServiceException {
        QueryBuilder queryBuilder = getQueryBuilder(request);
        request.setAttribute(EVENTS, eventService.getSortedByUser(queryBuilder.getQuery(), SPEAKER));
        int numberOfRecords = eventService.getNumberOfRecords(queryBuilder.getRecordQuery(), SPEAKER);
        paginate(numberOfRecords, request);
        return VIEW_SPEAKERS_EVENTS_PAGE;
    }

    private QueryBuilder getQueryBuilder(HttpServletRequest request) {
        return eventQueryBuilder()
                .setUserIdFilter(((UserDTO) request.getSession().getAttribute(LOGGED_USER)).getId())
                .setDateFilter(request.getParameter(DATE))
                .setSortField(request.getParameter(SORT))
                .setOrder(request.getParameter(ORDER))
                .setLimits(request.getParameter(OFFSET), request.getParameter(RECORDS));
    }
}