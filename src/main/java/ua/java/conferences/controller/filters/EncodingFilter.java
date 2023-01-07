package ua.java.conferences.controller.filters;

import jakarta.servlet.*;

import java.io.IOException;

/**
 * EncodingFilter  class. Sets encoding for any values from view
 *
 * @author Vitalii Kalinchyk
 * @version 1.0
 */
public class EncodingFilter implements Filter {
    private String encoding;

    /**
     * Sets default encoding
     * @param config passed by application
     */
    @Override
    public void init(FilterConfig config) {
        encoding = config.getInitParameter("encoding");
    }

    /**
     * Sets default encoding for any values from user.
     * @param request passed by application
     * @param response passed by application
     * @param chain passed by application
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        request.setCharacterEncoding(encoding);
        chain.doFilter(request, response);
    }
}