package ua.java.conferences.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import ua.java.conferences.filters.domains.*;

import java.io.IOException;

import static ua.java.conferences.actions.constants.ActionConstants.ROLE;
import static ua.java.conferences.actions.constants.Pages.*;

public class AuthorizationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String role = (String) httpRequest.getSession().getAttribute(ROLE);
        if (role != null && isAccessDenied(httpRequest, role)) {
            request.getRequestDispatcher(PROFILE_PAGE).forward(request, response);
        } else {
            chain.doFilter(request, response);
        }
    }

    private boolean isAccessDenied(HttpServletRequest httpRequest, String role) {
        Domain domain = DomainFactory.getRoleDomain(httpRequest, role);
        return (domain.checkAccess());
    }
}