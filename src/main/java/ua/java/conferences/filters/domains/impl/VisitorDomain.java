package ua.java.conferences.filters.domains.impl;

import ua.java.conferences.filters.domains.Domain;
import ua.java.conferences.filters.domains.sets.*;
import java.util.Set;

public class VisitorDomain extends Domain {
    Set<String> domainPages = DomainPagesSets.getVisitorPages();
    Set<String> domainActions = DomainActionsSets.getVisitorActions();

    public VisitorDomain(String servletPath, String action) {
        super(servletPath, action);
    }

    public boolean checkPages() {
        if (servletPath != null) {
            return domainPages.contains(servletPath.substring(1));
        }
        return true;
    }

    public boolean checkActions() {
        if (action != null) {
            return domainActions.contains(action);
        }
        return true;
    }
}