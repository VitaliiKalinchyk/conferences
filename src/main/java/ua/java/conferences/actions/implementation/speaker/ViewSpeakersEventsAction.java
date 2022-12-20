package ua.java.conferences.actions.implementation.speaker;

import jakarta.servlet.http.HttpServletRequest;
import ua.java.conferences.actions.Action;
import ua.java.conferences.dto.*;
import ua.java.conferences.exceptions.ServiceException;
import ua.java.conferences.services.*;
import ua.java.conferences.utils.query.QueryBuilder;

import static ua.java.conferences.actions.ActionUtil.*;
import static ua.java.conferences.actions.constants.Pages.*;
import static ua.java.conferences.actions.constants.Parameters.*;
import static ua.java.conferences.entities.role.Role.*;
import static ua.java.conferences.utils.PaginationUtil.paginate;
import static ua.java.conferences.utils.QueryBuilderUtil.*;

public class ViewSpeakersEventsAction implements Action {
    private final EventService eventService;

    public ViewSpeakersEventsAction() {
        eventService = ServiceFactory.getInstance(DB_IMPLEMENTATION).getEventService();
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