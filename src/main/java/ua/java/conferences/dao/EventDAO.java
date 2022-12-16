package ua.java.conferences.dao;

import ua.java.conferences.entities.*;
import ua.java.conferences.exceptions.DAOException;
import ua.java.conferences.utils.sorting.Sorting;

import java.util.*;

public interface EventDAO extends EntityDAO<Event> {

    Optional<Event> getByTitle(String title) throws DAOException;

    List<Event> getSorted(Sorting sorting, int offset, int records) throws DAOException;

    List<Event> getSortedByUser(long userId, Sorting sorting, int offset, int records, String role) throws DAOException;

    int getNumberOfRecords(long id, Sorting sorting, String role) throws DAOException;

    void setVisitorsCount(long eventId, int visitorsCount) throws DAOException;
}