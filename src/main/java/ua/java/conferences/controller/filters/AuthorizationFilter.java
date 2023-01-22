package ua.java.conferences.controller.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

import static ua.java.conferences.controller.actions.constants.Pages.SIGN_IN_PAGE;
import static ua.java.conferences.controller.actions.constants.ParameterValues.ACCESS_DENIED;
import static ua.java.conferences.controller.actions.constants.Parameters.*;
import static ua.java.conferences.controller.filters.domain.Domain.*;

/**
 * AuthorizationFilter class. Controls access to pages for logged user
 *
 * @author Vitalii Kalinchyk
 * @version 1.0
 */
public class AuthorizationFilter implements Filter {

    /**
     * Checks for role in session and then checks if user has access to page or action.
     * @param request passed by application
     * @param response passed by application
     * @param chain passed by application
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String role = (String) httpRequest.getSession().getAttribute(ROLE);
        String servletPath = httpRequest.getServletPath();
        String action = httpRequest.getParameter(ACTION);
        if (role != null && isAccessDenied(servletPath, action, role)) {
            httpRequest.setAttribute(MESSAGE, ACCESS_DENIED);
            request.getRequestDispatcher(SIGN_IN_PAGE).forward(request, response);
        } else {
            chain.doFilter(request, response);
        }
    }

    private boolean isAccessDenied(String servletPath, String action, String role) {
        return (getDomain(servletPath, action, role).checkAccess());
    }
}