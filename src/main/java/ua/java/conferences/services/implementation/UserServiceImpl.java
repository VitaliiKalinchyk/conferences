package ua.java.conferences.services.implementation;

import ua.java.conferences.dao.UserDAO;
import ua.java.conferences.services.UserService;
import ua.java.conferences.entities.User;
import ua.java.conferences.entities.role.Role;
import ua.java.conferences.exceptions.*;

import java.util.List;

import static ua.java.conferences.entities.role.Role.*;

public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;

    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public boolean add(User user) throws ServiceException {
        boolean result;
        try {
            result = userDAO.add(user);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public User getById(long id) throws ServiceException {
        User user;
        try {
            user = userDAO.getById(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return user;
    }

    @Override
    public List<User> getAll() throws ServiceException {
        List<User> users;
        try {
            users = userDAO.getAll();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return users;
    }

    @Override
    public boolean update(User user) throws ServiceException {
        boolean result;
        try {
            result = userDAO.update(user);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public boolean delete(long id) throws ServiceException {
        boolean result;
        try {
            result = userDAO.delete(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public User getByEmail(String email) throws ServiceException {
        User user;
        try {
            user = userDAO.getByEmail(email);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return user;
    }

    @Override
    public User getSpeakerByReport(long reportId) throws ServiceException {
        User user;
        try {
            user = userDAO.getSpeakerByReport(reportId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return user;
    }

    @Override
    public List<User> getVisitors() throws ServiceException {
        List<User> users;
        try {
            users = userDAO.getUsersByRole(VISITOR);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return users;
    }

    @Override
    public List<User> getSpeakers() throws ServiceException {
        List<User> users;
        try {
            users = userDAO.getUsersByRole(Role.SPEAKER);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return users;
    }

    @Override
    public List<User> getModerators() throws ServiceException {
        List<User> users;
        try {
            users = userDAO.getUsersByRole(Role.MODERATOR);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return users;
    }

    @Override
    public List<User> getUsersByEvent(long eventId) throws ServiceException {
        List<User> users;
        try {
            users = userDAO.getUsersByEvent(eventId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return users;
    }

    @Override
    public boolean registerForEvent(long userId, long eventId) throws ServiceException {
        boolean result;
        try {
            result = userDAO.registerForEvent(userId, eventId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public Role getUsersRole(long userId) throws ServiceException {
        Role role;
        try {
            role = userDAO.getUsersRole(userId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return role;
    }

    @Override
    public boolean setUserAsVisitor(long userId) throws ServiceException {
        boolean result;
        try {
            result = userDAO.setUsersRole(userId, VISITOR);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public boolean setUserAsSpeaker(long userId) throws ServiceException {
        boolean result;
        try {
            result = userDAO.setUsersRole(userId, SPEAKER);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public boolean setUserAsModerator(long userId) throws ServiceException {
        boolean result;
        try {
            result = userDAO.setUsersRole(userId, MODERATOR);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return result;
    }
}