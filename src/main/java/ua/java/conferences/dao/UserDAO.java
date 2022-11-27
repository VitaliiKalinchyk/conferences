package ua.java.conferences.dao;

import ua.java.conferences.entities.*;
import ua.java.conferences.entities.role.Role;
import ua.java.conferences.exceptions.DAOException;

import java.util.*;

public interface UserDAO extends EntityDAO<User> {

    Optional<User> getByEmail(String email) throws DAOException;

    List<User> getSpeakers() throws DAOException;

    void updateEmail(User user) throws DAOException;

    void updatePassword(User user) throws DAOException;

    void setUsersRole(long userId, Role role) throws DAOException;

    void registerForEvent(long userId, long eventId) throws DAOException;

    boolean isRegistered(long userId, long eventId) throws DAOException;
}