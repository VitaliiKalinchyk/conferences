package ua.java.conferences.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import ua.java.conferences.filters.domains.*;

import java.io.IOException;

import static ua.java.conferences.actions.constants.ActionConstants.LOGGED_USER;
import static ua.java.conferences.actions.constants.Pages.*;

public class AuthenticationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        if (isNoLoggedUser(httpRequest) && isAccessDenied(httpRequest)) {
                request.getRequestDispatcher(SIGN_IN_PAGE).forward(request, response);
        } else {
            chain.doFilter(request, response);
        }
    }

    private static boolean isNoLoggedUser(HttpServletRequest httpRequest) {
        return httpRequest.getSession().getAttribute(LOGGED_USER) == null;
    }

    private boolean isAccessDenied(HttpServletRequest httpRequest) {
        Domain domain = DomainFactory.getAnonymousDomain(httpRequest);
        return (domain.checkAccess());
    }
}