package ua.java.conferences.controller.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import ua.java.conferences.controller.actions.MyRequest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class LocaleFilterTest {
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final FilterChain chain = mock(FilterChain.class);
    private final FilterConfig config = mock(FilterConfig.class);

    @Test
    void testDoFilterNoLocale() throws ServletException, IOException {
        when(config.getInitParameter("defaultLocale")).thenReturn("en");
        LocaleFilter filter = new LocaleFilter();
        filter.init(config);
        MyRequest myRequest = new MyRequest(request);
        filter.doFilter(myRequest, response, chain);
        assertEquals("en", myRequest.getSession().getAttribute("locale"));
    }

    @Test
    void testDoFilterUaLocale() throws ServletException, IOException {
        when(config.getInitParameter("defaultLocale")).thenReturn("en");
        when(request.getParameter("locale")).thenReturn("uk_UA");
        when(request.getServletPath()).thenReturn("index.jsp");
        LocaleFilter filter = new LocaleFilter();
        filter.init(config);
        MyRequest myRequest = new MyRequest(request);
        filter.doFilter(myRequest, response, chain);
        assertEquals("uk_UA", myRequest.getSession().getAttribute("locale"));
    }
}