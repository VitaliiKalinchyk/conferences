package ua.java.conferences.controller.actions.implementation.admin;

import jakarta.servlet.http.HttpServletRequest;
import ua.java.conferences.controller.context.AppContext;
import ua.java.conferences.controller.actions.Action;
import ua.java.conferences.exceptions.ServiceException;
import ua.java.conferences.model.services.*;
import ua.java.conferences.model.utils.query.QueryBuilder;

import static ua.java.conferences.controller.actions.constants.Pages.*;
import static ua.java.conferences.controller.actions.constants.Parameters.*;
import static ua.java.conferences.model.utils.PaginationUtil.*;
import static ua.java.conferences.model.utils.QueryBuilderUtil.*;

public class ViewUsersAction implements Action {
    private final UserService userService;

    public ViewUsersAction(AppContext appContext) {
        userService = appContext.getUserService();
    }

    @Override
    public String execute(HttpServletRequest request) throws ServiceException {
        QueryBuilder queryBuilder = getQueryBuilder(request);
        request.setAttribute(USERS, userService.getSortedUsers(queryBuilder.getQuery()));
        int numberOfRecords = userService.getNumberOfRecords(queryBuilder.getRecordQuery());
        paginate(numberOfRecords, request);
        return VIEW_USERS_PAGE;
    }

    private QueryBuilder getQueryBuilder(HttpServletRequest request) {
        return userQueryBuilder()
                .setRoleFilter(request.getParameter(ROLE))
                .setSortField(request.getParameter(SORT))
                .setOrder(request.getParameter(ORDER))
                .setLimits(request.getParameter(OFFSET), request.getParameter(RECORDS));
    }
}
