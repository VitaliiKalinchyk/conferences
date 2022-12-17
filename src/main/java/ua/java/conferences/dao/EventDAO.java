package ua.java.conferences.dao;

import ua.java.conferences.entities.*;
import ua.java.conferences.entities.role.Role;
import ua.java.conferences.exceptions.DAOException;

import java.util.*;

public interface EventDAO extends EntityDAO<Event> {

    Optional<Event> getByTitle(String title) throws DAOException;

    List<Event> getSorted(String query) throws DAOException;

    List<Event> getSortedByUser(String query, Role role) throws DAOException;

    int getNumberOfRecords(String filter, Role role) throws DAOException;

    void setVisitorsCount(long eventId, int visitorsCount) throws DAOException;
}