package ua.java.conferences.filters.domains.impl;

import jakarta.servlet.http.HttpServletRequest;
import ua.java.conferences.filters.domains.Domain;
import ua.java.conferences.filters.domains.sets.DomainActionsSets;
import ua.java.conferences.filters.domains.sets.DomainPagesSets;

import java.util.Set;

public class AdminDomain implements Domain {

    Set<String> domainPages = DomainPagesSets.getAdminPages();

    Set<String> domainActions = DomainActionsSets.getAdminActions();

    HttpServletRequest request;
    public AdminDomain(HttpServletRequest request) {
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