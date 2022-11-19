package ua.java.conferences.services;

import ua.java.conferences.entities.*;
import ua.java.conferences.entities.role.Role;
import ua.java.conferences.exceptions.*;

import java.util.List;

public interface UserService extends Service<User> {

    User getByEmail(String email) throws ServiceException;

    User getSpeakerByReport(long reportId) throws ServiceException;

    List<User> getVisitors() throws ServiceException;

    List<User> getSpeakers() throws ServiceException;

    List<User> getModerators() throws ServiceException;

    List<User> getUsersByEvent(long eventId) throws ServiceException;

    boolean registerForEvent(long userId, long eventId) throws ServiceException;

    Role getUsersRole(long userId) throws ServiceException;

    boolean setUserAsVisitor(long userId) throws ServiceException;

    boolean setUserAsSpeaker(long userId) throws ServiceException;

    boolean setUserAsModerator(long userId) throws ServiceException;
}