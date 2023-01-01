package ua.java.conferences.controller.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.slf4j.*;

import java.io.IOException;

import static ua.java.conferences.controller.actions.constants.Parameters.LOCALE;

public class LocaleFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(LocaleFilter.class);
    private static final String REFERER = "referer";
    private String defaultLocale;

    @Override
    public void init(FilterConfig config) {
        logger.info("default locale is set");
        defaultLocale = config.getInitParameter("defaultLocale");
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String locale = httpRequest.getParameter(LOCALE);
        if (isNotBlank(locale)) {
            httpRequest.getSession().setAttribute(LOCALE, locale);
            logger.info("locale is changed");
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