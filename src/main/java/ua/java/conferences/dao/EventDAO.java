package ua.java.conferences.dao;

import ua.java.conferences.entity.*;
import ua.java.conferences.exception.DAOException;

import java.util.List;

public interface EventDAO extends EntityDAO<Event> {

    Event getByTitle(String title) throws DAOException;

    boolean setVisitors(long eventId, int visitors) throws DAOException;

    List<Event> getEventsByUser(long userId) throws DAOException;

    List<Event> getEventsBySpeaker(long userId) throws DAOException;

    Event getEventByReport(long reportId) throws DAOException;
}