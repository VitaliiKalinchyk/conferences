package ua.java.conferences.controller.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ua.java.conferences.controller.actions.MyRequest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doNothing;
import static ua.java.conferences.controller.actions.constants.Pages.SIGN_IN_PAGE;
import static ua.java.conferences.controller.actions.constants.ParameterValues.ACCESS_DENIED;
import static ua.java.conferences.controller.actions.constants.Parameters.*;

class AuthorizationFilterTest {
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final RequestDispatcher dispatcher = mock(RequestDispatcher.class);
    private final FilterChain chain = mock(FilterChain.class);

    @ParameterizedTest
    @ValueSource(strings = {"/index.jsp", "/about.jsp", "/contacts.jsp", "/signIn.jsp", "/signUp.jsp",
            "/resetPassword.jsp", "/error.jsp", "/profile.jsp", "/editProfile.jsp", "/changePassword.jsp"})
    void testVisitorDoFilterCorrectPage(String page) throws ServletException, IOException {
        AuthorizationFilter filter = new AuthorizationFilter();
        MyRequest myRequest = new MyRequest(request);
        myRequest.getSession().setAttribute(ROLE, "VISITOR");
        when(request.getServletPath()).thenReturn(page);
        doNothing().when(chain).doFilter(myRequest, response);
        filter.doFilter(myRequest, response, chain);
        assertNull(myRequest.getAttribute(MESSAGE));
    }

    @ParameterizedTest
    @ValueSource(strings = {"sign-in", "sign-up", "password-reset", "sign-out", "edit-profile", "change-password",
            "register-or-cancel"})
    void testVisitorDoFilterCorrectAction(String action) throws ServletException, IOException {
        AuthorizationFilter filter = new AuthorizationFilter();
        MyRequest myRequest = new MyRequest(request);
        myRequest.getSession().setAttribute(ROLE, "VISITOR");
        when(request.getParameter(ACTION)).thenReturn(action);
        doNothing().when(chain).doFilter(myRequest, response);
        filter.doFilter(myRequest, response, chain);
        assertNull(myRequest.getAttribute(MESSAGE));
    }

    @ParameterizedTest
    @ValueSource(strings = {"/createEvent.jsp", "/searchEvent.jsp", "/viewEvent.jsp", "/viewUsers.jsp"})
    void testVisitorDoFilterAccessDeniedPage(String page) throws ServletException, IOException {
        AuthorizationFilter filter = new AuthorizationFilter();
        MyRequest myRequest = new MyRequest(request);
        myRequest.getSession().setAttribute(ROLE, "VISITOR");
        when(request.getServletPath()).thenReturn(page);
        when(request.getRequestDispatcher(SIGN_IN_PAGE)).thenReturn(dispatcher);
        doNothing().when(dispatcher).forward(myRequest, response);
        filter.doFilter(myRequest, response, chain);
        assertEquals(ACCESS_DENIED, myRequest.getAttribute(MESSAGE));
    }

    @ParameterizedTest
    @ValueSource(strings = {"offer-report", "edit-event", "search-user"})
    void testVisitorDoFilterAccessDeniedAction(String action) throws ServletException, IOException {
        AuthorizationFilter filter = new AuthorizationFilter();
        MyRequest myRequest = new MyRequest(request);
        myRequest.getSession().setAttribute(ROLE, "VISITOR");
        when(request.getParameter(ACTION)).thenReturn(action);
        when(request.getRequestDispatcher(SIGN_IN_PAGE)).thenReturn(dispatcher);
        doNothing().when(dispatcher).forward(myRequest, response);
        filter.doFilter(myRequest, response, chain);
        assertEquals(ACCESS_DENIED, myRequest.getAttribute(MESSAGE));
    }

    @ParameterizedTest
    @ValueSource(strings = {"/index.jsp", "/about.jsp", "/contacts.jsp", "/signIn.jsp", "/signUp.jsp",
            "/resetPassword.jsp", "/error.jsp", "/profile.jsp", "/editProfile.jsp", "/changePassword.jsp"})
    void testSpeakerDoFilterCorrectPage(String page) throws ServletException, IOException {
        AuthorizationFilter filter = new AuthorizationFilter();
        MyRequest myRequest = new MyRequest(request);
        myRequest.getSession().setAttribute(ROLE, "SPEAKER");
        when(request.getServletPath()).thenReturn(page);
        doNothing().when(chain).doFilter(myRequest, response);
        filter.doFilter(myRequest, response, chain);
        assertNull(myRequest.getAttribute(MESSAGE));
    }

    @ParameterizedTest
    @ValueSource(strings = {"sign-in", "sign-up", "password-reset", "sign-out", "edit-profile", "change-password",
            "offer-report"})
    void testSpeakerDoFilterCorrectAction(String action) throws ServletException, IOException {
        AuthorizationFilter filter = new AuthorizationFilter();
        MyRequest myRequest = new MyRequest(request);
        myRequest.getSession().setAttribute(ROLE, "SPEAKER");
        when(request.getParameter(ACTION)).thenReturn(action);
        doNothing().when(chain).doFilter(myRequest, response);
        filter.doFilter(myRequest, response, chain);
        assertNull(myRequest.getAttribute(MESSAGE));
    }

    @ParameterizedTest
    @ValueSource(strings = {"/createEvent.jsp", "/searchEvent.jsp", "/viewEvent.jsp", "/viewUsers.jsp"})
    void testSpeakerDoFilterAccessDeniedPage(String page) throws ServletException, IOException {
        AuthorizationFilter filter = new AuthorizationFilter();
        MyRequest myRequest = new MyRequest(request);
        myRequest.getSession().setAttribute(ROLE, "SPEAKER");
        when(request.getServletPath()).thenReturn(page);
        when(request.getRequestDispatcher(SIGN_IN_PAGE)).thenReturn(dispatcher);
        doNothing().when(dispatcher).forward(myRequest, response);
        filter.doFilter(myRequest, response, chain);
        assertEquals(ACCESS_DENIED, myRequest.getAttribute(MESSAGE));
    }

    @ParameterizedTest
    @ValueSource(strings = {"register-or-cancel", "edit-event", "search-user"})
    void testSpeakerDoFilterAccessDeniedAction(String action) throws ServletException, IOException {
        AuthorizationFilter filter = new AuthorizationFilter();
        MyRequest myRequest = new MyRequest(request);
        myRequest.getSession().setAttribute(ROLE, "SPEAKER");
        when(request.getParameter(ACTION)).thenReturn(action);
        when(request.getRequestDispatcher(SIGN_IN_PAGE)).thenReturn(dispatcher);
        doNothing().when(dispatcher).forward(myRequest, response);
        filter.doFilter(myRequest, response, chain);
        assertEquals(ACCESS_DENIED, myRequest.getAttribute(MESSAGE));
    }

    @ParameterizedTest
    @ValueSource(strings = {"/index.jsp", "/about.jsp", "/contacts.jsp", "/signIn.jsp", "/signUp.jsp",
            "/resetPassword.jsp", "/error.jsp", "/profile.jsp", "/editProfile.jsp",
            "/createEvent.jsp", "/searchEvent.jsp", "/viewEvent.jsp"})
    void testModeratorDoFilterCorrectPage(String page) throws ServletException, IOException {
        AuthorizationFilter filter = new AuthorizationFilter();
        MyRequest myRequest = new MyRequest(request);
        myRequest.getSession().setAttribute(ROLE, "MODERATOR");
        when(request.getServletPath()).thenReturn(page);
        doNothing().when(chain).doFilter(myRequest, response);
        filter.doFilter(myRequest, response, chain);
        assertNull(myRequest.getAttribute(MESSAGE));
    }

    @ParameterizedTest
    @ValueSource(strings = {"sign-in", "sign-up", "password-reset", "sign-out", "edit-profile", "change-password",
            "view-events", "create-event", "edit-event", "create-report"})
    void testModeratorDoFilterCorrectAction(String action) throws ServletException, IOException {
        AuthorizationFilter filter = new AuthorizationFilter();
        MyRequest myRequest = new MyRequest(request);
        myRequest.getSession().setAttribute(ROLE, "MODERATOR");
        when(request.getParameter(ACTION)).thenReturn(action);
        doNothing().when(chain).doFilter(myRequest, response);
        filter.doFilter(myRequest, response, chain);
        assertNull(myRequest.getAttribute(MESSAGE));
    }

    @ParameterizedTest
    @ValueSource(strings = {"/searchUser.jsp", "/viewUsers.jsp"})
    void testModeratorDoFilterAccessDeniedPage(String page) throws ServletException, IOException {
        AuthorizationFilter filter = new AuthorizationFilter();
        MyRequest myRequest = new MyRequest(request);
        myRequest.getSession().setAttribute(ROLE, "MODERATOR");
        when(request.getServletPath()).thenReturn(page);
        when(request.getRequestDispatcher(SIGN_IN_PAGE)).thenReturn(dispatcher);
        doNothing().when(dispatcher).forward(myRequest, response);
        filter.doFilter(myRequest, response, chain);
        assertEquals(ACCESS_DENIED, myRequest.getAttribute(MESSAGE));
    }

    @ParameterizedTest
    @ValueSource(strings = {"register-or-cancel", "offer-report", "search-user"})
    void testModeratorDoFilterAccessDeniedAction(String action) throws ServletException, IOException {
        AuthorizationFilter filter = new AuthorizationFilter();
        MyRequest myRequest = new MyRequest(request);
        myRequest.getSession().setAttribute(ROLE, "MODERATOR");
        when(request.getParameter(ACTION)).thenReturn(action);
        when(request.getRequestDispatcher(SIGN_IN_PAGE)).thenReturn(dispatcher);
        doNothing().when(dispatcher).forward(myRequest, response);
        filter.doFilter(myRequest, response, chain);
        assertEquals(ACCESS_DENIED, myRequest.getAttribute(MESSAGE));
    }

    @ParameterizedTest
    @ValueSource(strings = {"/index.jsp", "/about.jsp", "/contacts.jsp", "/signIn.jsp", "/signUp.jsp",
            "/resetPassword.jsp", "/error.jsp", "/profile.jsp", "/editProfile.jsp",
            "/viewUsers.jsp", "/searchUser.jsp"})
    void testAdminDoFilterCorrectPage(String page) throws ServletException, IOException {
        AuthorizationFilter filter = new AuthorizationFilter();
        MyRequest myRequest = new MyRequest(request);
        myRequest.getSession().setAttribute(ROLE, "ADMIN");
        when(request.getServletPath()).thenReturn(page);
        doNothing().when(chain).doFilter(myRequest, response);
        filter.doFilter(myRequest, response, chain);
        assertNull(myRequest.getAttribute(MESSAGE));
    }

    @ParameterizedTest
    @ValueSource(strings = {"sign-in", "sign-up", "password-reset", "sign-out", "edit-profile", "change-password",
            "view-users", "set-role", "delete-user", "search-user"})
    void testAdminDoFilterCorrectAction(String action) throws ServletException, IOException {
        AuthorizationFilter filter = new AuthorizationFilter();
        MyRequest myRequest = new MyRequest(request);
        myRequest.getSession().setAttribute(ROLE, "ADMIN");
        when(request.getParameter(ACTION)).thenReturn(action);
        doNothing().when(chain).doFilter(myRequest, response);
        filter.doFilter(myRequest, response, chain);
        assertNull(myRequest.getAttribute(MESSAGE));
    }

    @ParameterizedTest
    @ValueSource(strings = {"/createEvent.jsp", "/searchEvent.jsp", "/viewEvent.jsp"})
    void testAdminDoFilterAccessDeniedPage(String page) throws ServletException, IOException {
        AuthorizationFilter filter = new AuthorizationFilter();
        MyRequest myRequest = new MyRequest(request);
        myRequest.getSession().setAttribute(ROLE, "ADMIN");
        when(request.getServletPath()).thenReturn(page);
        when(request.getRequestDispatcher(SIGN_IN_PAGE)).thenReturn(dispatcher);
        doNothing().when(dispatcher).forward(myRequest, response);
        filter.doFilter(myRequest, response, chain);
        assertEquals(ACCESS_DENIED, myRequest.getAttribute(MESSAGE));
    }

    @ParameterizedTest
    @ValueSource(strings = {"register-or-cancel", "offer-report", "search-event"})
    void testAdminDoFilterAccessDeniedAction(String action) throws ServletException, IOException {
        AuthorizationFilter filter = new AuthorizationFilter();
        MyRequest myRequest = new MyRequest(request);
        myRequest.getSession().setAttribute(ROLE, "ADMIN");
        when(request.getParameter(ACTION)).thenReturn(action);
        when(request.getRequestDispatcher(SIGN_IN_PAGE)).thenReturn(dispatcher);
        doNothing().when(dispatcher).forward(myRequest, response);
        filter.doFilter(myRequest, response, chain);
        assertEquals(ACCESS_DENIED, myRequest.getAttribute(MESSAGE));
    }

    @Test
    void testDoFilterNoLoggedUser() throws ServletException, IOException {
        AuthorizationFilter filter = new AuthorizationFilter();
        MyRequest myRequest = new MyRequest(request);
        doNothing().when(chain).doFilter(myRequest, response);
        filter.doFilter(myRequest, response, chain);
        assertNull(myRequest.getAttribute(MESSAGE));
    }
}