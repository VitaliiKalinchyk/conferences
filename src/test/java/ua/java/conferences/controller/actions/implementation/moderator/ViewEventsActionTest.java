package ua.java.conferences.controller.actions.implementation.moderator;

import jakarta.servlet.http.*;
import org.junit.jupiter.api.Test;
import ua.java.conferences.controller.actions.MyRequest;
import ua.java.conferences.controller.context.AppContext;
import ua.java.conferences.dto.EventDTO;
import ua.java.conferences.exceptions.ServiceException;
import ua.java.conferences.model.entities.role.Role;
import ua.java.conferences.model.services.EventService;
import ua.java.conferences.utils.query.QueryBuilder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static ua.java.conferences.controller.actions.constants.Pages.VIEW_EVENTS_PAGE;
import static ua.java.conferences.controller.actions.constants.ParameterValues.*;
import static ua.java.conferences.controller.actions.constants.Parameters.*;
import static ua.java.conferences.utils.QueryBuilderUtil.eventQueryBuilder;

class ViewEventsActionTest {
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final AppContext appContext = mock(AppContext.class);
    private final EventService eventService = mock(EventService.class);
    private final String ZERO = "0";
    private final String FIVE = "5";

    @Test
    void testExecute() throws ServiceException {
        setRequest();
        MyRequest myRequest = new MyRequest(request);
        when(appContext.getEventService()).thenReturn(eventService);
        when(eventService.getSorted(getQueryBuilder().getQuery())).thenReturn(getEvents());
        when(eventService.getNumberOfRecords(getQueryBuilder().getRecordQuery(), Role.MODERATOR)).thenReturn(10);

        assertEquals(VIEW_EVENTS_PAGE, new ViewEventsAction(appContext).execute(myRequest, response));
        assertEquals(getEvents(), myRequest.getAttribute(EVENTS));
        assertEquals(0, myRequest.getAttribute(OFFSET));
        assertEquals(5, myRequest.getAttribute(RECORDS));
        assertEquals(2, myRequest.getAttribute(PAGES));
        assertEquals(1, myRequest.getAttribute(CURRENT_PAGE));
        assertEquals(1, myRequest.getAttribute(START));
        assertEquals(2, myRequest.getAttribute(END));
    }

    private void setRequest() {
        when(request.getParameter(DATE)).thenReturn(PASSED);
        when(request.getParameter(SORT)).thenReturn(TITLE);
        when(request.getParameter(ORDER)).thenReturn(DESCENDING_ORDER);
        when(request.getParameter(OFFSET)).thenReturn(ZERO);
        when(request.getParameter(RECORDS)).thenReturn(FIVE);
    }

    private List<EventDTO> getEvents(){
        List<EventDTO> events = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            events.add(EventDTO.builder().id(i).build());
        }
        return events;
    }

    private QueryBuilder getQueryBuilder() {
        return eventQueryBuilder()
                .setDateFilter(PASSED)
                .setSortField(TITLE)
                .setOrder(DESCENDING_ORDER)
                .setLimits(ZERO, FIVE);
    }
}