package ua.java.conferences.controller.actions.implementation.admin;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.*;
import org.junit.jupiter.api.Test;
import ua.java.conferences.controller.actions.*;
import ua.java.conferences.controller.context.AppContext;
import ua.java.conferences.dto.UserDTO;
import ua.java.conferences.exceptions.ServiceException;
import ua.java.conferences.model.services.UserService;
import ua.java.conferences.utils.PdfUtil;
import ua.java.conferences.utils.query.QueryBuilder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static ua.java.conferences.controller.actions.ActionUtil.getActionToRedirect;
import static ua.java.conferences.controller.actions.constants.ActionNames.VIEW_USERS_ACTION;
import static ua.java.conferences.controller.actions.constants.Parameters.*;
import static ua.java.conferences.utils.QueryBuilderUtil.userQueryBuilder;

class UsersToPdfActionTest {
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final AppContext appContext = mock(AppContext.class);
    private final UserService userService = mock(UserService.class);
    private final ServletContext servletContext = mock(ServletContext.class);
    private final PdfUtil pdfUtil = new PdfUtil();

    @Test
    void testExecute() throws ServiceException, MalformedURLException {
        setRequest();
        MyResponse myResponse = new MyResponse(response);
        MyRequest myRequest = new MyRequest(request);
        myRequest.getSession().setAttribute(LOCALE, "en");
        when(appContext.getUserService()).thenReturn(userService);
        when(appContext.getPdfUtil()).thenReturn(pdfUtil);
        when(appContext.getServletContext()).thenReturn(servletContext);
        when(servletContext.getResource("fonts/arial.ttf")).thenThrow(MalformedURLException.class);
        when(userService.getSortedUsers(getQueryBuilder().getQuery())).thenReturn(getUsers());

        String path = new UsersToPdfAction(appContext).execute(myRequest, myResponse);
        assertEquals(getActionToRedirect(VIEW_USERS_ACTION), path);

        String expected = pdfUtil.createUsersPdf(getUsers(), servletContext, "en").toString();
        String actual = myResponse.getOutputStream().toString();
        assertEquals(clearString(expected), clearString(actual));
    }

    @Test
    void testExecuteIOException() throws ServiceException, IOException {
        setRequest();
        MyRequest myRequest = new MyRequest(request);
        myRequest.getSession().setAttribute(LOCALE, "en");
        when(appContext.getUserService()).thenReturn(userService);
        when(appContext.getPdfUtil()).thenReturn(pdfUtil);
        when(appContext.getServletContext()).thenReturn(servletContext);
        when(servletContext.getResource("fonts/arial.ttf")).thenThrow(MalformedURLException.class);
        when(userService.getSortedUsers(getQueryBuilder().getQuery())).thenReturn(getUsers());
        when(response.getOutputStream()).thenThrow(IOException.class);

        String path = new UsersToPdfAction(appContext).execute(myRequest, response);
        assertEquals(getActionToRedirect(VIEW_USERS_ACTION), path);
    }

    private void setRequest() {
        when(request.getParameter(ROLE)).thenReturn("3");
        when(request.getParameter(SORT)).thenReturn("name");
        when(request.getParameter(ORDER)).thenReturn("asc");
    }


    private QueryBuilder getQueryBuilder() {
        String zero = "0";
        String max = String.valueOf(Integer.MAX_VALUE);
        return userQueryBuilder()
                .setRoleFilter("3")
                .setSortField("name")
                .setOrder("asc")
                .setLimits(zero, max);
    }


    private List<UserDTO> getUsers(){
        List<UserDTO> users = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            users.add(UserDTO.builder()
                    .id(i).email("some email").name("some name").surname("some surname").role("some role")
                    .build());
        }
        return users;
    }

    private String clearString(String string) {
        return string
                .lines()
                .filter(s -> !(s.contains("CreationDate") || s.contains("ID")))
                .collect(Collectors.joining("\n"));
    }
}