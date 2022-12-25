package ua.java.conferences.controller.actions.implementation.moderator;

import jakarta.servlet.http.HttpServletRequest;
import ua.java.conferences.controller.context.AppContext;
import ua.java.conferences.controller.actions.Action;
import ua.java.conferences.dto.UserDTO;
import ua.java.conferences.exceptions.*;
import ua.java.conferences.model.entities.role.Role;
import ua.java.conferences.model.services.*;
import ua.java.conferences.utils.EmailSender;

import static ua.java.conferences.controller.actions.ActionUtil.*;
import static ua.java.conferences.controller.actions.constants.ActionNames.*;
import static ua.java.conferences.controller.actions.constants.Pages.*;
import static ua.java.conferences.controller.actions.constants.ParameterValues.*;
import static ua.java.conferences.controller.actions.constants.Parameters.*;
import static ua.java.conferences.utils.constants.Email.*;

public class DeleteEventAction implements Action {
    private final EventService eventService;
    private final UserService userService;
    private final EmailSender emailSender;

    public DeleteEventAction(AppContext appContext) {
        eventService = appContext.getEventService();
        userService = appContext.getUserService();
        emailSender = appContext.getEmailSender();
    }

    @Override
    public String execute(HttpServletRequest request) throws ServiceException {
        return isPostMethod(request) ? executePost(request) : executeGet(request);
    }

    private String executeGet(HttpServletRequest request) {
        transferStringFromSessionToRequest(request, MESSAGE);
        return SEARCH_EVENT_PAGE;
    }

    private String executePost(HttpServletRequest request) throws ServiceException {
        String eventId = request.getParameter(EVENT_ID);
        String title = request.getParameter(TITLE);
        eventService.delete(eventId);
        request.getSession().setAttribute(MESSAGE, SUCCEED_DELETE);
        sendEmail(eventId, title);
        return getActionToRedirect(DELETE_EVENT_ACTION);
    }


    private void sendEmail(String eventId, String title) throws ServiceException {
        for (UserDTO participant : userService.getParticipants(eventId, Role.VISITOR)) {
            String body = String.format(MESSAGE_EVENT_DELETED, participant.getName(), title);
            emailSender.send(SUBJECT_NOTIFICATION, body, participant.getEmail());
        }
        for (UserDTO participant : userService.getParticipants(eventId, Role.SPEAKER)) {
            String body = String.format(MESSAGE_EVENT_DELETED, participant.getName(), title);
            emailSender.send(SUBJECT_NOTIFICATION, body, participant.getEmail());
        }

    }
}