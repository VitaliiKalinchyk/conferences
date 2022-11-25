package ua.java.conferences.services.implementation;

import ua.java.conferences.dao.*;
import ua.java.conferences.dto.request.UserRequestDTO;
import ua.java.conferences.dto.response.UserResponseDTO;
import ua.java.conferences.entities.User;
import ua.java.conferences.entities.role.Role;
import ua.java.conferences.exceptions.*;
import ua.java.conferences.services.UserService;

import java.util.*;

import static ua.java.conferences.utils.ConvertorUtil.*;
import static ua.java.conferences.utils.PasswordHashUtil.*;
import static ua.java.conferences.utils.ValidatorUtil.*;

public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;

    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public void register(UserRequestDTO userDTO) throws ServiceException {
        validateUser(userDTO);
        User user = convertDTOToUser(userDTO);
        try {
            userDAO.add(user);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public UserResponseDTO signIn(String login, String password) throws ServiceException {
        UserResponseDTO userDTO;
        try {
            User user = userDAO.getByEmail(login).orElse(null);
            if (Objects.isNull(user)) {
                throw new IncorrectEmailException();
            }
            if (!verify(user.getPassword(), password)) {
                throw new IncorrectPasswordException();
            }
            userDTO = convertUserToDTO(user);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return userDTO;
    }

    @Override
    public UserResponseDTO viewProfile(long userId) throws ServiceException {
        UserResponseDTO userDTO;
        try {
            User user = userDAO.getById(userId).orElse(null);
            if (user == null) {
                throw new NoSuchUserException();
            }
            userDTO = convertUserToDTO(user);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return userDTO;
    }

    @Override
    public UserResponseDTO searchUser(String email) throws ServiceException {
        UserResponseDTO userDTO;
        try {
            User user = userDAO.getByEmail(email).orElse(null);
            if (user == null) {
                throw new NoSuchUserException();
            }
            userDTO = convertUserToDTO(user);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return userDTO;
    }

    @Override
    public List<UserResponseDTO> viewUsers() throws ServiceException {
        List<UserResponseDTO> userDTOS = new ArrayList<>();
        try {
            List<User> users = userDAO.getAll();
            users.forEach(user -> userDTOS.add(convertUserToDTO(user)));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return userDTOS;
    }

    @Override
    public List<UserResponseDTO> viewSpeakers() throws ServiceException {
        List<UserResponseDTO> userDTOS = new ArrayList<>();
        try {
            List<User> users = userDAO.getSpeakers();
            users.forEach(user -> userDTOS.add(convertUserToDTO(user)));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return userDTOS;
    }

    @Override
    public UserResponseDTO editProfile(UserRequestDTO userDTO) throws ServiceException {
        validateUser(userDTO);
        User user = convertDTOToUser(userDTO);
        try {
            userDAO.update(user);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return convertUserToDTO(user);
    }

    @Override
    public void setRole(long userId, int roleId) throws ServiceException {
        try {
            Role role = Role.getRole(roleId);
            userDAO.setUsersRole(userId, role);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteUser(long userId) throws ServiceException {
        try {
            userDAO.delete(userId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void registerForEvent(long userId, long eventId) throws ServiceException {
        try {
            userDAO.registerForEvent(userId, eventId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean isRegistered(long userId, long eventId) throws ServiceException {
        boolean result;
        try {
            result = userDAO.isRegistered(userId, eventId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    private void validateUser(UserRequestDTO userDTO) throws IncorrectFormatException {
        if (!validateEmail(userDTO.email)) {
            throw new IncorrectFormatException("email");
        }
        if (!validatePassword(userDTO.password)) {
            throw new IncorrectFormatException("password");
        }
        if (!validateName(userDTO.name)) {
            throw new IncorrectFormatException("name");
        }
        if (!validateName(userDTO.surname)) {
            throw new IncorrectFormatException("surname");
        }
    }
}