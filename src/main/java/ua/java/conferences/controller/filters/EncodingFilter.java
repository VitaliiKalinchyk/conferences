package ua.java.conferences.controller.filters;

import jakarta.servlet.*;
import org.slf4j.*;

import java.io.IOException;

public class EncodingFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(EncodingFilter.class);
    private String encoding;

    @Override
    public void init(FilterConfig config) {
        encoding = config.getInitParameter("encoding");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        request.setCharacterEncoding(encoding);
        logger.info("encoding is set");
        chain.doFilter(request, response);
    }
}