package ua.java.conferences.controller.actions.implementation.moderator;

import jakarta.servlet.ServletContext;
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

public class EventsToPdfAction implements Action {
    private static final Logger logger = LoggerFactory.getLogger(EventsToPdfAction.class);
    private final EventService eventService;
    private final PdfUtil pdfUtil;
    private final ServletContext servletContext;

    public EventsToPdfAction(AppContext appContext) {
        eventService = appContext.getEventService();
        pdfUtil = appContext.getPdfUtil();
        servletContext = appContext.getServletContext();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        QueryBuilder queryBuilder = getQueryBuilder(request);
        List<EventDTO> events = eventService.getSorted(queryBuilder.getQuery());
        String locale = (String) request.getSession().getAttribute(LOCALE);
        ByteArrayOutputStream usersPdf = pdfUtil.createEventsPdf(events, servletContext, locale);
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
        response.setContentType("application/pdf");
        response.setContentLength(output.size());
        response.setHeader("Content-Disposition", "attachment; filename=\"events.pdf\"");
        try (OutputStream outputStream = response.getOutputStream()) {
            output.writeTo(outputStream);
            outputStream.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}