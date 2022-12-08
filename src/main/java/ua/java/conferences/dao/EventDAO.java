package ua.java.conferences.dao;

import ua.java.conferences.entities.*;
import ua.java.conferences.exceptions.DAOException;

import java.util.*;

public interface EventDAO extends EntityDAO<Event> {

    Optional<Event> getByTitle(String title) throws DAOException;

    void setVisitorsCount(long eventId, int visitorsCount) throws DAOException;

    List<Event> getSortedEvents(String filter, String sortField, String order) throws DAOException;

    List<Event> getEventsByVisitor(long userId) throws DAOException;

    List<Event> getPastEventsByVisitor(long userId) throws DAOException;

    List<Event> getEventsBySpeaker(long speakerId) throws DAOException;
}