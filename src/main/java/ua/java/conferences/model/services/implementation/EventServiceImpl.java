package ua.java.conferences.model.services.implementation;

import ua.java.conferences.model.dao.EventDAO;
import ua.java.conferences.dto.EventDTO;
import ua.java.conferences.model.entities.Event;
import ua.java.conferences.model.entities.role.Role;
import ua.java.conferences.exceptions.*;
import ua.java.conferences.model.services.EventService;

import java.time.LocalDate;
import java.util.*;

import static ua.java.conferences.exceptions.constants.Message.*;
import static ua.java.conferences.utils.ConvertorUtil.*;
import static ua.java.conferences.utils.ValidatorUtil.*;

public class EventServiceImpl implements EventService {
    private final EventDAO eventDAO;

    public EventServiceImpl(EventDAO eventDAO) {
        this.eventDAO = eventDAO;
    }

    @Override
    public void addEvent(ua.java.conferences.dto.EventDTO eventDTO) throws ServiceException {
        validateEvent(eventDTO);
        Event event = convertDTOToEvent(eventDTO);
        try {
            eventDAO.add(event);
        } catch (DAOException e) {
            checkExceptionType(e);
        }
    }

    @Override
    public EventDTO getById(String eventIdString) throws ServiceException {
        long eventId = getEventId(eventIdString);
        EventDTO eventDTO;
        try {
            Event event = eventDAO.getById(eventId).orElseThrow(NoSuchEventException::new);
            eventDTO = convertEventToDTO(event);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return eventDTO;
    }

    @Override
    public EventDTO getByTitle(String title) throws ServiceException {
        EventDTO eventDTO;
        try {
            Event event = eventDAO.getByTitle(title).orElseThrow(NoSuchEventException::new);
            eventDTO = convertEventToDTO(event);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return eventDTO;
    }

    @Override
    public List<EventDTO> getAll() throws ServiceException {
        List<EventDTO> eventDTOS = new ArrayList<>();
        try {
            List<Event> events = eventDAO.getAll();
            events.forEach(event -> eventDTOS.add(convertEventToDTO(event)));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return eventDTOS;
    }

    @Override
    public List<EventDTO> getSorted(String query) throws ServiceException {
        List<EventDTO> eventDTOS = new ArrayList<>();
        try {
            List<Event> events = eventDAO.getSorted(query);
            events.forEach(event -> eventDTOS.add(convertEventToDTO(event)));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return eventDTOS;
    }

    @Override
    public List<EventDTO> getSortedByUser(String query, Role role)
            throws ServiceException {
        List<EventDTO> eventDTOS = new ArrayList<>();
        try {
            List<Event> events = eventDAO.getSortedByUser(query, role);
            events.forEach(event -> eventDTOS.add(convertEventToDTO(event)));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return eventDTOS;
    }

    @Override
    public int getNumberOfRecords(String filter, Role role) throws ServiceException {
        int records;
        try {
            records = eventDAO.getNumberOfRecords(filter, role);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return records;
    }

    @Override
    public void update(EventDTO dto) throws ServiceException {
        validateEvent(dto);
        Event event = convertDTOToEvent(dto);
        try {
            eventDAO.update(event);
        } catch (DAOException e) {
            checkExceptionType(e);
        }
    }

    @Override
    public void setVisitorsCount(String eventIdString, String visitorsCountString) throws ServiceException {
        long eventId = getEventId(eventIdString);
        int visitorsCount = getInt(visitorsCountString);
        try {
            eventDAO.setVisitorsCount(eventId, visitorsCount);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void delete(String eventIdString) throws ServiceException {
        long eventId = getEventId(eventIdString);
        try {
            eventDAO.delete(eventId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    private void checkExceptionType(DAOException e) throws ServiceException {
        if (e.getMessage() != null && e.getMessage().contains("Duplicate")) {
            throw new DuplicateTitleException();
        } else {
            throw new ServiceException(e);
        }
    }

    private void validateEvent(ua.java.conferences.dto.EventDTO eventDTO) throws IncorrectFormatException {
        validateComplexName(eventDTO.getTitle(), ENTER_CORRECT_TITLE);
        validateDate(LocalDate.parse(eventDTO.getDate()));
        validateComplexName(eventDTO.getLocation(), ENTER_CORRECT_LOCATION);
        validateDescription(eventDTO.getDescription());
    }
}