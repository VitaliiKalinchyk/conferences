package ua.java.conferences.model.dao;

import ua.java.conferences.model.entities.User;
import ua.java.conferences.model.entities.role.Role;
import ua.java.conferences.exceptions.DAOException;

import java.util.*;

public interface UserDAO extends EntityDAO<User> {

    Optional<User> getByEmail(String email) throws DAOException;

    List<User> getSorted(String query) throws DAOException;
    List<User> getParticipants(long eventId, Role role) throws DAOException;

    int getNumberOfRecords(String filter) throws DAOException;

    void updatePassword(User user) throws DAOException;

    void setUserRole(String userEmail, Role role) throws DAOException;

    void registerForEvent(long userId, long eventId) throws DAOException;

    void cancelRegistration(long userId, long eventId) throws DAOException;

    boolean isRegistered(long userId, long eventId) throws DAOException;
}