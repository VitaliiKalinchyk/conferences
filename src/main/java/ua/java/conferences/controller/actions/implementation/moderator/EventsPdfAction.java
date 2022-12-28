package ua.java.conferences.controller.actions.implementation.moderator;

import jakarta.servlet.http.*;
import org.slf4j.*;
import ua.java.conferences.controller.actions.Action;
import ua.java.conferences.controller.context.AppContext;
import ua.java.conferences.dto.EventDTO;
import ua.java.conferences.exceptions.ServiceException;
import ua.java.conferences.model.services.EventService;
import ua.java.conferences.utils.PdfUtil;
import ua.java.conferences.utils.query.QueryBuilder;

import java.io.*;
import java.util.List;

import static ua.java.conferences.controller.actions.ActionUtil.getActionToRedirect;
import static ua.java.conferences.controller.actions.constants.ActionNames.VIEW_EVENTS_ACTION;
import static ua.java.conferences.controller.actions.constants.Parameters.*;
import static ua.java.conferences.utils.QueryBuilderUtil.eventQueryBuilder;

public class EventsPdfAction implements Action {
    private static final Logger logger = LoggerFactory.getLogger(EventsPdfAction.class);
    private final EventService eventService;
    private final PdfUtil pdfUtil;

    public EventsPdfAction(AppContext appContext) {
        eventService = appContext.getEventService();
        pdfUtil = appContext.getPdfUtil();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        QueryBuilder queryBuilder = getQueryBuilder(request);
        List<EventDTO> events = eventService.getSorted(queryBuilder.getQuery());
        ByteArrayOutputStream usersPdf = pdfUtil.createEventsPdf(events);
        setResponse(response, usersPdf);
        return getActionToRedirect(VIEW_EVENTS_ACTION);
    }

    private QueryBuilder getQueryBuilder(HttpServletRequest request) {
        String zero = "0";
        String max = String.valueOf(Integer.MAX_VALUE);
        return eventQueryBuilder()
                .setDateFilter(request.getParameter(DATE))
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
