package ua.java.conferences.controller.actions.implementation.admin;

import jakarta.servlet.http.*;
import org.slf4j.*;
import ua.java.conferences.controller.actions.Action;
import ua.java.conferences.controller.context.AppContext;
import ua.java.conferences.dto.UserDTO;
import ua.java.conferences.exceptions.ServiceException;
import ua.java.conferences.model.services.UserService;
import ua.java.conferences.utils.PdfUtil;
import ua.java.conferences.utils.query.QueryBuilder;

import java.io.*;
import java.util.List;

import static ua.java.conferences.controller.actions.ActionUtil.getActionToRedirect;
import static ua.java.conferences.controller.actions.constants.ActionNames.VIEW_USERS_ACTION;
import static ua.java.conferences.controller.actions.constants.Parameters.*;
import static ua.java.conferences.utils.QueryBuilderUtil.userQueryBuilder;

public class UsersPdfAction implements Action {
    private static final Logger logger = LoggerFactory.getLogger(UsersPdfAction.class);
    private final UserService userService;
    private final PdfUtil pdfUtil;

    public UsersPdfAction(AppContext appContext) {
        userService = appContext.getUserService();
        pdfUtil = appContext.getPdfUtil();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        QueryBuilder queryBuilder = getQueryBuilder(request);
        List<UserDTO> users = userService.getSortedUsers(queryBuilder.getQuery());
        ByteArrayOutputStream usersPdf = pdfUtil.createUsersPdf(users);
        setResponse(response, usersPdf);
        return getActionToRedirect(VIEW_USERS_ACTION);
    }

    private QueryBuilder getQueryBuilder(HttpServletRequest request) {
        String zero = "0";
        String max = String.valueOf(Integer.MAX_VALUE);
        return userQueryBuilder()
                .setRoleFilter(request.getParameter(ROLE))
                .setSortField(request.getParameter(SORT))
                .setOrder(request.getParameter(ORDER))
                .setLimits(zero, max);
    }

    private void setResponse(HttpServletResponse response, ByteArrayOutputStream output) {
        response.setContentLength(output.size());
        try (OutputStream outputStream = response.getOutputStream()) {
            output.writeTo(outputStream);
            outputStream.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
