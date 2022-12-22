package ua.java.conferences.model.dao;

import ua.java.conferences.model.entities.Event;
import ua.java.conferences.model.entities.role.Role;
import ua.java.conferences.exceptions.DAOException;

import java.util.*;

public interface EventDAO extends EntityDAO<Event> {

    Optional<Event> getByTitle(String title) throws DAOException;

    List<Event> getSorted(String query) throws DAOException;

    List<Event> getSortedByUser(String query, Role role) throws DAOException;

    int getNumberOfRecords(String filter, Role role) throws DAOException;

    void setVisitorsCount(long eventId, int visitorsCount) throws DAOException;
}