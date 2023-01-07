package ua.java.conferences.controller.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.IOException;

import static ua.java.conferences.controller.actions.constants.Parameters.LOCALE;

/**
 * LocaleFilter  class. Sets and changes locale
 *
 * @author Vitalii Kalinchyk
 * @version 1.0
 */
public class LocaleFilter implements Filter {
    private static final String REFERER = "referer";
    private String defaultLocale;

    /**
     * Sets default locale
     * @param config passed by application
     */
    @Override
    public void init(FilterConfig config) {
        defaultLocale = config.getInitParameter("defaultLocale");
    }

    /**
     * Checks if request contains locale attribute and changes locale if present. Returns previous page in this case.
     * doFilter if not.
     * @param request passed by application
     * @param response passed by application
     * @param chain passed by application
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String locale = httpRequest.getParameter(LOCALE);
        if (isNotBlank(locale)) {
            httpRequest.getSession().setAttribute(LOCALE, locale);
            ((HttpServletResponse) response).sendRedirect(((HttpServletRequest) request).getHeader(REFERER));
        } else {
            String sessionLocale = (String) httpRequest.getSession().getAttribute(LOCALE);
            if (isBlank(sessionLocale)) {
                httpRequest.getSession().setAttribute(LOCALE, defaultLocale);
            }
            chain.doFilter(request, response);
        }
    }

    private boolean isBlank(String locale) {
        return locale == null || locale.isEmpty();
    }

    private boolean isNotBlank(String locale) {
        return !isBlank(locale);
    }
}