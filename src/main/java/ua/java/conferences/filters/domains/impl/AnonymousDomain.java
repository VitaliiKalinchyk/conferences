package ua.java.conferences.filters.domains.impl;

import jakarta.servlet.http.HttpServletRequest;
import ua.java.conferences.filters.domains.Domain;
import ua.java.conferences.filters.domains.sets.*;

import java.util.Set;

public class AnonymousDomain implements Domain {

    Set<String> domainPages = DomainPagesSets.getAnonymousUserPages();

    Set<String> domainActions = DomainActionsSets.getAnonymousUserActions();

    HttpServletRequest request;

    public AnonymousDomain(HttpServletRequest request) {
        this.request = request;
    }

    public boolean checkPages() {
        String servletPath = request.getServletPath();
        if (servletPath != null) {
            return domainPages.contains(servletPath.substring(1));
        }
        return true;
    }

    public boolean checkActions() {
        String action = request.getParameter("action");
        if (action != null) {
            return domainActions.contains(action);
        }
        return true;
    }
}