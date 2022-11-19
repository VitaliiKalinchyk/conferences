package ua.java.conferences.dao;

import ua.java.conferences.entities.*;
import ua.java.conferences.exceptions.DAOException;

import java.util.List;

public interface EventDAO extends EntityDAO<Event> {

    Event getByTitle(String title) throws DAOException;

    boolean setVisitorsCount(long eventId, int visitorsCount) throws DAOException;

    List<Event> getEventsByUser(long userId) throws DAOException;

    List<Event> getEventsBySpeaker(long userId) throws DAOException;

    Event getEventByReport(long reportId) throws DAOException;
}