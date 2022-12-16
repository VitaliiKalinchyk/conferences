package ua.java.conferences.filters.domains;

import ua.java.conferences.entities.role.Role;
import ua.java.conferences.filters.domains.impl.*;

public final class DomainFactory {
    private DomainFactory() {}

    public static Domain getAnonymousDomain(String servletPath, String action) {
        return new AnonymousDomain(servletPath, action);
    }

    public static Domain getRoleDomain(String servletPath, String action, String role) {
        switch (Role.valueOf(role)) {
            case VISITOR: return new VisitorDomain(servletPath, action);
            case SPEAKER: return new SpeakerDomain(servletPath, action);
            case MODERATOR: return new ModeratorDomain(servletPath, action);
            case ADMIN: return new AdminDomain(servletPath, action);
            default: return new AnonymousDomain(servletPath, action);
        }
    }
}