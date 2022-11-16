package ua.java.conferences.dao;

import ua.java.conferences.entity.*;
import ua.java.conferences.exception.DAOException;

import java.util.List;

public interface EventDAO extends EntityDAO<Event> {

    Event getByTitle(Event event) throws DAOException;

    boolean setVisitors(Event event, int visitors) throws DAOException;

    List<Event> getEventsByUser(User user) throws DAOException;

    List<Event> getEventsBySpeaker(User user) throws DAOException;

    Event getEventByReport(Report report) throws DAOException;
}