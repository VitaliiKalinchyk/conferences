package ua.java.conferences.controller.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;

import static ua.java.conferences.controller.actions.constants.Pages.SIGN_IN_PAGE;
import static ua.java.conferences.controller.actions.constants.ParameterValues.ACCESS_DENIED;
import static ua.java.conferences.controller.actions.constants.Parameters.*;
import static ua.java.conferences.controller.filters.domain.Domain.*;

public class AuthorizationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String role = (String) httpRequest.getSession().getAttribute(ROLE);
        if (role != null && isAccessDenied(httpRequest, role)) {
            httpRequest.setAttribute(MESSAGE, ACCESS_DENIED);
            request.getRequestDispatcher(SIGN_IN_PAGE).forward(request, response);
        } else {
            chain.doFilter(request, response);
        }
    }

    private boolean isAccessDenied(HttpServletRequest request, String role) {
        return (getDomain(request.getServletPath(), request.getParameter(ACTION), role).checkAccess());
    }
}