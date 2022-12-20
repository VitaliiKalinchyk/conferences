package ua.java.conferences.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EncodingFilterTest {
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final FilterChain chain = mock(FilterChain.class);
    private final FilterConfig config = mock(FilterConfig.class);

    @Test
    void testDoFilter() throws ServletException, IOException {
        when(config.getInitParameter("encoding")).thenReturn("UTF-8");
        EncodingFilter filter = new EncodingFilter();
        filter.init(config);
        MyRequest myRequest = new MyRequest(request);
        filter.doFilter(myRequest, response, chain);
        assertEquals("UTF-8", myRequest.getCharacterEncoding());
    }

    private static class MyRequest extends HttpServletRequestWrapper {
        private String encoding;

        public MyRequest(HttpServletRequest request) {
            super(request);
        }

        @Override
        public String getCharacterEncoding() {
            return encoding;
        }

        @Override
        public void setCharacterEncoding(String enc) {
            encoding = enc;
        }
    }
}