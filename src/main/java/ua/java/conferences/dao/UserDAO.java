package ua.java.conferences.dao;

import ua.java.conferences.entities.*;
import ua.java.conferences.entities.role.Role;
import ua.java.conferences.exceptions.DAOException;

import java.util.List;

public interface UserDAO extends EntityDAO<User> {

    User getByEmail(String email) throws DAOException;

    User getSpeakerByReport(long reportId) throws DAOException;

    List<User> getUsersByRole(Role role) throws DAOException;

    List<User> getUsersByEvent(long eventId) throws DAOException;

    boolean registerForEvent(long userId, long eventId) throws DAOException;

    Role getUsersRole(long userId) throws DAOException;

    boolean setUsersRole(long userId, Role role) throws DAOException;
}