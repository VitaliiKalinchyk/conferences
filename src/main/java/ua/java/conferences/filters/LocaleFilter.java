package ua.java.conferences.filters;


import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.IOException;

public class LocaleFilter implements Filter {

    private static final String LOCALE = "locale";

    private static final String REFRESH = "Refresh";

    private static final int REFRESH_TIME = 0;

    private String defaultLocale;

    @Override
    public void init(FilterConfig config) {
        defaultLocale = config.getInitParameter("defaultLocale");
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String locale = httpRequest.getParameter(LOCALE);
        if (isNotBlank(locale)) {
            ((HttpServletResponse)response).setIntHeader(REFRESH, REFRESH_TIME);
            httpRequest.getSession().setAttribute(LOCALE, locale);
        } else {
            String sessionLocale = (String) httpRequest.getSession().getAttribute(LOCALE);
            if (isBlank(sessionLocale)) {
                httpRequest.getSession().setAttribute(LOCALE, defaultLocale);
            }
        }
        chain.doFilter(request, response);
    }

    private boolean isBlank(String locale) {
        return locale == null || locale.isEmpty();
    }

    private boolean isNotBlank(String locale) {
        return !isBlank(locale);
    }
}