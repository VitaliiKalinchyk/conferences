package ua.java.conferences.controller.actions.implementation.moderator;

import jakarta.servlet.http.HttpServletRequest;
import ua.java.conferences.controller.context.AppContext;
import ua.java.conferences.controller.actions.Action;
import ua.java.conferences.dto.EventDTO;
import ua.java.conferences.dto.UserDTO;
import ua.java.conferences.exceptions.*;
import ua.java.conferences.model.entities.role.Role;
import ua.java.conferences.model.services.*;
import ua.java.conferences.utils.EmailSender;

import java.time.LocalDate;

import static ua.java.conferences.controller.actions.ActionUtil.*;
import static ua.java.conferences.controller.actions.constants.ActionNames.*;
import static ua.java.conferences.controller.actions.constants.Pages.*;
import static ua.java.conferences.controller.actions.constants.Parameters.*;
import static ua.java.conferences.controller.actions.constants.ParameterValues.*;
import static ua.java.conferences.utils.constants.Email.*;

public class EditEventAction implements Action {
    private final EventService eventService;
    private final UserService userService;
    private final EmailSender emailSender;

    public EditEventAction(AppContext appContext) {
        eventService = appContext.getEventService();
        userService = appContext.getUserService();
        emailSender = appContext.getEmailSender();
    }

    @Override
    public String execute(HttpServletRequest request) throws ServiceException {
        return isPostMethod(request) ? executePost(request) : executeGet(request);
    }

    private String executeGet(HttpServletRequest request) throws ServiceException {
        transferAttributes(request);
        String eventId = request.getParameter(EVENT_ID);
        try {
            request.setAttribute(EVENT, getEvent(eventId));
        } catch (NoSuchEventException e) {
            request.setAttribute(ERROR, e.getMessage());
        }
        return EDIT_EVENT_PAGE;
    }

    private EventDTO getEvent(String eventId) throws ServiceException {
        EventDTO event = eventService.getById(eventId);
        if (LocalDate.now().isAfter(LocalDate.parse(event.getDate()))) {
            throw new NoSuchEventException(ERROR_EVENT_EDIT);
        }
        return event;
    }

    private void transferAttributes(HttpServletRequest request) {
        transferStringFromSessionToRequest(request, MESSAGE);
        transferStringFromSessionToRequest(request, ERROR);
        transferEventDTOFromSessionToRequest(request, EVENT_NEW);
    }

    private String executePost(HttpServletRequest request) throws ServiceException {
        EventDTO event = getEventDTO(request);
        try {
            eventService.update(event);
            request.getSession().setAttribute(MESSAGE, SUCCEED_UPDATE);
            sendEmail(event);
        } catch (IncorrectFormatException | DuplicateTitleException e) {
            request.getSession().setAttribute(EVENT_NEW, event);
            request.getSession().setAttribute(ERROR, e.getMessage());
        }
        return getActionToRedirect(EDIT_EVENT_ACTION, EVENT_ID, String.valueOf(event.getId()));
    }

    private EventDTO getEventDTO(HttpServletRequest request) {
        return EventDTO.builder()
                .id(Long.parseLong(request.getParameter(EVENT_ID)))
                .title(request.getParameter(TITLE))
                .date(request.getParameter(DATE))
                .location(request.getParameter(LOCATION))
                .description(request.getParameter(DESCRIPTION))
                .build();
    }

    private void sendEmail(EventDTO event) throws ServiceException {
        String eventId = String.valueOf(event.getId());
        String title = event.getTitle();
        String date = event.getDate();
        String location = event.getLocation();
        String description = event.getDescription();
        for (UserDTO participant : userService.getParticipants(eventId, Role.VISITOR)) {
            new Thread(
                    () -> {
                        String body = String.format(MESSAGE_EVENT_CHANGED_VISITOR, participant.getName(), title,
                                date, location, description, eventId);
                        emailSender.send(SUBJECT_NOTIFICATION, body, participant.getEmail());})
                    .start();

        }
        for (UserDTO participant : userService.getParticipants(eventId, Role.SPEAKER)) {
            new Thread(
                    () -> {
                        String body = String.format(MESSAGE_EVENT_CHANGED_SPEAKER, participant.getName(), title,
                                date, location, description, eventId);
                        emailSender.send(SUBJECT_NOTIFICATION, body, participant.getEmail());})
                    .start();
        }
    }
}