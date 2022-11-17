package ua.java.conferences.dao;

import ua.java.conferences.entity.*;
import ua.java.conferences.entity.role.Role;
import ua.java.conferences.exception.DAOException;

import java.util.List;

public interface UserDAO extends EntityDAO<User> {

    User getByEmail(String email) throws DAOException;

    User getSpeakerByReport(Report report) throws DAOException;

    List<User> getUsersByRole(Role role) throws DAOException;

    List<User> getUsersByEvent(Event event) throws DAOException;

    boolean registerForEvent(User user, Event event) throws DAOException;

    Role getUsersRole(User user) throws DAOException;

    boolean setUsersRole(User user, Role role) throws DAOException;
}