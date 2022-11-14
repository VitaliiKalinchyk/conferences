package ua.java.conferences.dao;

import ua.java.conferences.entity.*;
import ua.java.conferences.entity.role.Role;
import ua.java.conferences.exception.DBException;

import java.util.List;

public interface UserDAO extends EntityDAO<User> {

    boolean registerForEvent(User var1, Event var2) throws DBException;

    Role getUsersRole(User var1) throws DBException;

    boolean setUsersRole(User var1, Role var2) throws DBException;

    List<User> getUsersByRole(Role var1) throws DBException;

    List<User> getUsersByEvent(Event var1) throws DBException;

    User getSpeakerByReport(Report var1) throws DBException;
}