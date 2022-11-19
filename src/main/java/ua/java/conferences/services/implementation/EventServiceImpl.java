package ua.java.conferences.services.implementation;

import ua.java.conferences.dao.EventDAO;
import ua.java.conferences.entities.Event;
import ua.java.conferences.exceptions.*;
import ua.java.conferences.services.EventService;

import java.util.List;

public class EventServiceImpl implements EventService {

    private final EventDAO eventDAO;

    public EventServiceImpl(EventDAO eventDAO) {
        this.eventDAO = eventDAO;
    }

    @Override
    public boolean add(Event event) throws ServiceException {
        boolean result;
        try {
            result = eventDAO.add(event);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public Event getById(long id) throws ServiceException {
        Event event;
        try {
            event = eventDAO.getById(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return event;
    }

    @Override
    public List<Event> getAll() throws ServiceException {
        List<Event> events;
        try {
            events = eventDAO.getAll();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return events;
    }

    @Override
    public boolean update(Event event) throws ServiceException {
        boolean result;
        try {
            result = eventDAO.update(event);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public boolean delete(long id) throws ServiceException {
        boolean result;
        try {
            result = eventDAO.delete(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public Event getByTitle(String title) throws ServiceException {
        Event event;
        try {
            event = eventDAO.getByTitle(title);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return event;
    }

    @Override
    public boolean setVisitorsCount(long eventId, int visitorsCount) throws ServiceException {
        boolean result;
        try {
            result = eventDAO.setVisitorsCount(eventId, visitorsCount);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public List<Event> getEventsByUser(long userId) throws ServiceException {
        List<Event> events;
        try {
            events = eventDAO.getEventsByUser(userId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return events;
    }

    @Override
    public List<Event> getEventsBySpeaker(long userId) throws ServiceException {
        List<Event> events;
        try {
            events = eventDAO.getEventsBySpeaker(userId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return events;
    }

    @Override
    public Event getEventByReport(long reportId) throws ServiceException {
        Event event;
        try {
            event = eventDAO.getEventByReport(reportId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return event;
    }
}