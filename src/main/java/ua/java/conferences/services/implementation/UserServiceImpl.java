package ua.java.conferences.services.implementation;

import ua.java.conferences.dao.*;
import ua.java.conferences.dto.request.UserRequestDTO;
import ua.java.conferences.dto.response.UserResponseDTO;
import ua.java.conferences.entities.User;
import ua.java.conferences.entities.role.Role;
import ua.java.conferences.exceptions.*;
import ua.java.conferences.services.UserService;

import java.util.*;

import static ua.java.conferences.exceptions.IncorrectFormatException.Message.*;
import static ua.java.conferences.utils.ConvertorUtil.*;
import static ua.java.conferences.utils.PasswordHashUtil.*;
import static ua.java.conferences.utils.ValidatorUtil.*;

public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;

    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public void register(UserRequestDTO userDTO, String confirmPassword) throws ServiceException {
        validateUser(userDTO);
        checkPasswordMatching(userDTO.getPassword(), confirmPassword);
        User user = convertDTOToUser(userDTO);
        try {
            userDAO.add(user);
        } catch (DAOException e) {
            checkExceptionType(e);
        }
    }

    @Override
    public UserResponseDTO signIn(String login, String password) throws ServiceException {
        UserResponseDTO userDTO;
        try {
            User user = userDAO.getByEmail(login).orElseThrow(IncorrectEmailException::new);
            checkIfPasswordCorrect(password, user);
            userDTO = convertUserToDTO(user);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return userDTO;
    }

    @Override
    public UserResponseDTO view(long userId) throws ServiceException {
        UserResponseDTO userDTO;
        try {
            User user = userDAO.getById(userId).orElseThrow(NoSuchUserException::new);
            userDTO = convertUserToDTO(user);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return userDTO;
    }

    @Override
    public UserResponseDTO searchUser(String email) throws ServiceException {
        validate(validateEmail(email), ENTER_CORRECT_EMAIL);
        UserResponseDTO userDTO;
        try {
            User user = userDAO.getByEmail(email).orElseThrow(NoSuchUserException::new);
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
            checkExceptionType(e);
        }
        return convertUserToDTO(user);
    }

    @Override
    public void changePassword(long userId, String oldPassword, String newPassword, String confirmPassword)
            throws ServiceException {
        try {
            User user = userDAO.getById(userId).orElseThrow(NoSuchUserException::new);
            checkIfPasswordCorrect(oldPassword, user);
            checkPasswordMatching(newPassword, confirmPassword);
            validate(validatePassword(newPassword), ENTER_CORRECT_PASSWORD);
            User userToUpdate = new User.UserBuilder().setId(userId).setPassword(encode(newPassword)).get();
            userDAO.updatePassword(userToUpdate);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
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
    public void delete(long userId) throws ServiceException {
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
    public void cancelRegistration(long userId, long eventId) throws ServiceException {
        try {
            userDAO.cancelRegistration(userId, eventId);
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

    private void checkExceptionType(DAOException e) throws ServiceException {
        if (e.getMessage().contains("Duplicate")) {
            throw new DuplicateEmailException();
        } else {
            throw new ServiceException(e);
        }
    }

    private void checkPasswordMatching(String password, String confirmPassword) throws PasswordMatchingException {
        if (!password.equals(confirmPassword)) {
            throw new PasswordMatchingException();
        }
    }

    private void checkIfPasswordCorrect(String password, User user) throws IncorrectPasswordException {
        if (!verify(user.getPassword(), password)) {
            throw new IncorrectPasswordException();
        }
    }

    private void validate(boolean validation, String exceptionMessage) throws IncorrectFormatException {
        if (!validation) {
            throw new IncorrectFormatException(exceptionMessage);
        }
    }

    private void validateUser(UserRequestDTO userDTO) throws IncorrectFormatException {
        validate(validateEmail(userDTO.getEmail()), ENTER_CORRECT_EMAIL);
        if (userDTO.getPassword() != null) {
            validate(validatePassword(userDTO.getPassword()), ENTER_CORRECT_PASSWORD);
        }
        validate(validateName(userDTO.getName()), ENTER_CORRECT_NAME);
        validate(validateName(userDTO.getSurname()), ENTER_CORRECT_SURNAME);
    }
}