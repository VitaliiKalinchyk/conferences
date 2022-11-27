package ua.java.conferences.services.implementation;

import ua.java.conferences.dao.EventDAO;
import ua.java.conferences.dto.request.EventRequestDTO;
import ua.java.conferences.dto.response.*;
import ua.java.conferences.entities.Event;
import ua.java.conferences.exceptions.*;
import ua.java.conferences.services.EventService;

import java.time.LocalDate;
import java.util.*;

import static ua.java.conferences.dao.mysql.constants.EventConstants.UPCOMING;
import static ua.java.conferences.exceptions.IncorrectFormatException.Message.*;
import static ua.java.conferences.utils.ConvertorUtil.*;
import static ua.java.conferences.utils.ValidatorUtil.*;

public class EventServiceImpl implements EventService {

    private final EventDAO eventDAO;

    public EventServiceImpl(EventDAO eventDAO) {
        this.eventDAO = eventDAO;
    }

    @Override
    public EventResponseDTO createEvent(EventRequestDTO eventDTO) throws ServiceException {
        validateEvent(eventDTO);
        Event event = convertDTOToEvent(eventDTO);
        try {
            eventDAO.add(event);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return convertEventToDTO(event);
    }

    @Override
    public EventResponseDTO view(long eventId) throws ServiceException {
        EventResponseDTO eventDTO;
        try {
            Event event = eventDAO.getById(eventId).orElse(null);
            if (event == null) {
                throw new NoSuchEventException();
            }
            eventDTO = convertEventToDTO(event);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return eventDTO;
    }

    @Override
    public EventResponseDTO searchEvent(String title) throws ServiceException {
        EventResponseDTO eventDTO;
        try {
            Event event = eventDAO.getByTitle(title).orElse(null);
            if (event == null) {
                throw new NoSuchEventException();
            }
            eventDTO = convertEventToDTO(event);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return eventDTO;
    }

    @Override
    public List<EventResponseDTO> viewUsersEvents(long userId) throws ServiceException {
        List<EventResponseDTO> eventDTOS = new ArrayList<>();
        try {
            List<Event> events = eventDAO.getEventsByVisitor(userId);
            events.forEach(event -> eventDTOS.add(convertEventToDTO(event)));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return eventDTOS;
    }

    @Override
    public List<EventResponseDTO> viewSpeakersEvents(long speakerId) throws ServiceException {
        List<EventResponseDTO> eventDTOS = new ArrayList<>();
        try {
            List<Event> events = eventDAO.getEventsBySpeaker(speakerId);
            events.forEach(event -> eventDTOS.add(convertEventToDTO(event)));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return eventDTOS;
    }

    @Override
    public List<EventResponseDTO> viewSortedEventsByUser(String sortField, String order) throws ServiceException {
        List<EventResponseDTO> eventDTOS = new ArrayList<>();
        try {
            List<Event> events = eventDAO.getSortedEvents(UPCOMING, sortField, order);
            events.forEach(event -> eventDTOS.add(convertEventToDTO(event)));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return eventDTOS;
    }

    @Override
    public List<FullEventResponseDTO> viewSortedEventsByModerator(String filter, String sortField, String order) throws ServiceException {
        List<FullEventResponseDTO> eventDTOS = new ArrayList<>();
        try {
            List<Event> events = eventDAO.getSortedEvents(filter, sortField, order);
            events.forEach(event -> eventDTOS.add(convertEventToFullDTO(event)));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return eventDTOS;
    }

    @Override
    public EventResponseDTO editEvent(EventRequestDTO eventDTO) throws ServiceException {
        validateEvent(eventDTO);
        Event event = convertDTOToEvent(eventDTO);
        try {
            eventDAO.update(event);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return convertEventToDTO(event);
    }

    @Override
    public void setVisitorsCount(long eventId, int visitorsCount) throws ServiceException {
        try {
            eventDAO.setVisitorsCount(eventId, visitorsCount);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void delete(long eventId) throws ServiceException {
        try {
            eventDAO.delete(eventId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    private void validateEvent(EventRequestDTO eventDTO) throws IncorrectFormatException {
        if (!validateComplexName(eventDTO.title)) {
            throw new IncorrectFormatException(TITLE);
        }
        if (!validateDate(LocalDate.parse(eventDTO.date))) {
            throw new IncorrectFormatException(DATE);
        }
        if (!validateComplexName(eventDTO.location)) {
            throw new IncorrectFormatException(LOCATION);
        }
        if (!validateDescription(eventDTO.description)) {
            throw new IncorrectFormatException(DESCRIPTION);
        }
    }
}