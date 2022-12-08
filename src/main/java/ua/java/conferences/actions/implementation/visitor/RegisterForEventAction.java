package ua.java.conferences.actions.implementation.visitor;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.*;
import ua.java.conferences.actions.*;
import ua.java.conferences.dto.response.UserResponseDTO;
import ua.java.conferences.exceptions.ServiceException;
import ua.java.conferences.services.*;

import static ua.java.conferences.actions.constants.ActionNames.*;
import static ua.java.conferences.actions.constants.Pages.*;
import static ua.java.conferences.actions.constants.Parameters.*;
import static ua.java.conferences.dao.constants.DbImplementations.MYSQL;

public class RegisterForEventAction implements Action, ActionPost {

    private static final Logger logger = LoggerFactory.getLogger(RegisterForEventAction.class);

    private final UserService userService;

    public RegisterForEventAction() {
        userService = ServiceFactory.getInstance(MYSQL).getUserService();
    }

    @Override
    public String executeGet(HttpServletRequest request) {
        return new ViewEventByVisitorAction().executeGet(request);
    }

    @Override
    public String executePost(HttpServletRequest request) {
        String path;
        long userId = ((UserResponseDTO) request.getSession().getAttribute(LOGGED_USER)).getId();
        long eventId = Long.parseLong(request.getParameter(EVENT_ID));
        try {
            userService.registerForEvent(userId, eventId);
            return getRedirectControllerDirective(eventId);
        } catch (ServiceException e) {
            logger.error(e.getMessage());
            path = ERROR_PAGE;
        }
        return path;
    }

    private String getRedirectControllerDirective(long eventId) {
        return CONTROLLER_PAGE + "?" + ACTION + "=" + VIEW_EVENT_BY_VISITOR_ACTION + "&" + EVENT_ID + "=" + eventId;
    }
}
