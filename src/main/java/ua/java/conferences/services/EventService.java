package ua.java.conferences.services;

import ua.java.conferences.entities.Event;
import ua.java.conferences.exceptions.ServiceException;

import java.util.List;

public interface EventService extends Service<Event> {

    Event getByTitle(String title) throws ServiceException;

    boolean setVisitorsCount(long eventId, int visitorsCount) throws ServiceException;

    List<Event> getEventsByUser(long userId) throws ServiceException;

    List<Event> getEventsBySpeaker(long userId) throws ServiceException;

    Event getEventByReport(long reportId) throws ServiceException;
}