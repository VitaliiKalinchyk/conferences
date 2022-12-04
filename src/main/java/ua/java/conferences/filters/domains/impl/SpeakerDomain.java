package ua.java.conferences.filters.domains.impl;

import jakarta.servlet.http.HttpServletRequest;
import ua.java.conferences.filters.domains.Domain;
import ua.java.conferences.filters.domains.sets.DomainActionsSets;
import ua.java.conferences.filters.domains.sets.DomainPagesSets;

import java.util.Set;

public class SpeakerDomain implements Domain {

    Set<String> domainPages = DomainPagesSets.getSpeakerPages();

    Set<String> domainActions = DomainActionsSets.getSpeakerActions();

    HttpServletRequest request;

    public SpeakerDomain(HttpServletRequest request) {
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