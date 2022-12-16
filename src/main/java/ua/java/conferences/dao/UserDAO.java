package ua.java.conferences.dao;

import ua.java.conferences.entities.*;
import ua.java.conferences.entities.role.Role;
import ua.java.conferences.exceptions.DAOException;
import ua.java.conferences.utils.sorting.Sorting;

import java.util.*;

public interface UserDAO extends EntityDAO<User> {

    Optional<User> getByEmail(String email) throws DAOException;

    List<User> getSorted(Sorting sorting, int offset, int records)
            throws DAOException;

    int getNumberOfRecords(Sorting sorting) throws DAOException;

    void updatePassword(User user) throws DAOException;

    void setUserRole(String userEmail, Role role) throws DAOException;

    void registerForEvent(long userId, long eventId) throws DAOException;

    void cancelRegistration(long userId, long eventId) throws DAOException;

    boolean isRegistered(long userId, long eventId) throws DAOException;
}