package ua.java.conferences.controller.actions.implementation.moderator;

import jakarta.servlet.http.*;
import org.junit.jupiter.api.Test;
import ua.java.conferences.controller.actions.MyRequest;
import ua.java.conferences.controller.context.AppContext;
import ua.java.conferences.dto.*;
import ua.java.conferences.exceptions.*;
import ua.java.conferences.model.services.*;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;
import static ua.java.conferences.controller.actions.constants.Pages.VIEW_REPORT_PAGE;
import static ua.java.conferences.controller.actions.constants.ParameterValues.*;
import static ua.java.conferences.controller.actions.constants.Parameters.*;
import static ua.java.conferences.exceptions.constants.Message.NO_REPORT;

class ViewReportActionTest {
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final AppContext appContext = mock(AppContext.class);
    private final ReportService reportService = mock(ReportService.class);
    private final UserService userService = mock(UserService.class);

    @Test
    void testExecute() throws ServiceException {
        MyRequest myRequest = new MyRequest(request);
        setRequest();
        when(appContext.getReportService()).thenReturn(reportService);
        when(appContext.getUserService()).thenReturn(userService);
        when(reportService.getById("1")).thenReturn(getReport());
        when(userService.getSpeakers()).thenReturn(getSpeakers());
        String path = new ViewReportAction(appContext).execute(myRequest, response);

        assertEquals(VIEW_REPORT_PAGE, path);
        assertTrue((Boolean) myRequest.getAttribute(IS_COMING));
        assertEquals(getReport(), myRequest.getAttribute(REPORT));
        assertEquals(getSpeakers(), myRequest.getAttribute(SPEAKERS));
        assertNull(myRequest.getSession().getAttribute(MESSAGE));
        assertNull(myRequest.getSession().getAttribute(ERROR));
    }

    @Test
    void testBadExecute() throws ServiceException {
        MyRequest myRequest = new MyRequest(request);
        setRequest();
        when(appContext.getReportService()).thenReturn(reportService);
        doThrow(new NoSuchReportException()).when(reportService).getById(isA(String.class));
        String path = new ViewReportAction(appContext).execute(myRequest, response);

        assertEquals(VIEW_REPORT_PAGE, path);
        assertEquals(NO_REPORT, myRequest.getAttribute(ERROR));
        assertNull(myRequest.getAttribute(IS_COMING));
        assertNull(myRequest.getAttribute(REPORT));
        assertNull(myRequest.getAttribute(SPEAKERS));
        assertNull(myRequest.getSession().getAttribute(MESSAGE));
        assertNull(myRequest.getSession().getAttribute(ERROR));
    }

    @Test
    void testTransfer() throws ServiceException {
        MyRequest myRequest = new MyRequest(request);
        setRequest();
        HttpSession session = myRequest.getSession();
        session.setAttribute(MESSAGE, SUCCEED_ADD);
        session.setAttribute(ERROR, NO_REPORT);
        when(appContext.getReportService()).thenReturn(reportService);
        when(appContext.getUserService()).thenReturn(userService);
        when(reportService.getById("1")).thenReturn(getReport());
        when(userService.getSpeakers()).thenReturn(getSpeakers());
        new ViewReportAction(appContext).execute(myRequest, response);

        assertEquals(SUCCEED_ADD, myRequest.getAttribute(MESSAGE));
        assertEquals(NO_REPORT, myRequest.getAttribute(ERROR));
        assertNull(session.getAttribute(MESSAGE));
        assertNull(session.getAttribute(ERROR));
    }

    private void setRequest() {
        when(request.getParameter(REPORT_ID)).thenReturn("1");
    }

    private ReportDTO getReport() {
        return ReportDTO.builder()
                .id(1)
                .topic("topic")
                .date(String.valueOf(LocalDate.now().plusDays(1)))
                .build();
    }

    private List<UserDTO> getSpeakers() {
        List<UserDTO> speakers = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            speakers.add(UserDTO.builder().id(i).email("email" + i).build());
        }
        return speakers;
    }
}