package ua.java.conferences.filters.domains;

import jakarta.servlet.http.HttpServletRequest;
import ua.java.conferences.entities.role.Role;
import ua.java.conferences.filters.domains.impl.*;

public final class DomainFactory {

    private DomainFactory() {}

    public static Domain getAnonymousDomain(HttpServletRequest request) {
        return new AnonymousDomain(request);
    }

    public static Domain getRoleDomain(HttpServletRequest request, String role) {
        switch (Role.valueOf(role)) {
            case VISITOR: return new VisitorDomain(request);
            case SPEAKER: return new SpeakerDomain(request);
            case MODERATOR: return new ModeratorDomain(request);
            case ADMIN: return new AdminDomain(request);
            default: return new AnonymousDomain(request);
        }
    }
}