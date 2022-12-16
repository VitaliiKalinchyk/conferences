package ua.java.conferences.services.implementation;

import ua.java.conferences.dao.*;
import ua.java.conferences.dto.UserDTO;
import ua.java.conferences.entities.User;
import ua.java.conferences.entities.role.Role;
import ua.java.conferences.exceptions.*;
import ua.java.conferences.services.UserService;
import ua.java.conferences.utils.sorting.Sorting;

import java.util.*;

import static ua.java.conferences.actions.constants.ParameterValues.*;
import static ua.java.conferences.actions.constants.Parameters.*;
import static ua.java.conferences.exceptions.constants.Message.*;
import static ua.java.conferences.utils.ConvertorUtil.*;
import static ua.java.conferences.utils.PasswordHashUtil.*;
import static ua.java.conferences.utils.ValidatorUtil.*;

public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;

    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public void add(UserDTO userDTO, String confirmPassword) throws ServiceException {
        checkStrings(confirmPassword);
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
    public UserDTO signIn(String email, String password) throws ServiceException {
        checkStrings(email, password);
        UserDTO userDTO;
        try {
            User user = userDAO.getByEmail(email).orElseThrow(NoSuchUserException::new);
            verify(user.getPassword(), password);
            userDTO = convertUserToDTO(user);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return userDTO;
    }

    @Override
    public UserDTO getById(String userIdString) throws ServiceException {
        UserDTO userDTO;
        long userId = getUserId(userIdString);
        try {
            User user = userDAO.getById(userId).orElseThrow(NoSuchUserException::new);
            userDTO = convertUserToDTO(user);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return userDTO;
    }

    @Override
    public UserDTO getByEmail(String email) throws ServiceException {
        validateEmail(email);
        UserDTO userDTO;
        try {
            User user = userDAO.getByEmail(email).orElseThrow(NoSuchUserException::new);
            userDTO = convertUserToDTO(user);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return userDTO;
    }


    @Override
    public List<UserDTO> getAll() throws ServiceException {
        List<UserDTO> userDTOS = new ArrayList<>();
        try {
            List<User> users = userDAO.getAll();
            users.forEach(user -> userDTOS.add(convertUserToDTO(user)));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return userDTOS;
    }

    @Override
    public List<UserDTO> getSortedUsers(Sorting sorting, String offset, String records) throws ServiceException {
        List<UserDTO> userDTOS = new ArrayList<>();
        try {
            List<User> users = userDAO.getSorted(sorting, getInt(offset), getInt(records));
            users.forEach(user -> userDTOS.add(convertUserToDTO(user)));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return userDTOS;
    }

    @Override
    public int getNumberOfRecords(Sorting sorting) throws ServiceException {
        int records;
        try {
            records = userDAO.getNumberOfRecords(sorting);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return records;
    }

    @Override
    public List<UserDTO> getSpeakers() throws ServiceException {
        List<UserDTO> userDTOS = new ArrayList<>();
        Sorting sorting = Sorting.getUserSorting(String.valueOf(Role.SPEAKER.getValue()), ID, ASCENDING_ORDER);
        try {
            List<User> speakers = userDAO.getSorted(sorting, 0, Integer.MAX_VALUE);
            speakers.forEach(user -> userDTOS.add(convertUserToDTO(user)));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return userDTOS;
    }

    @Override
    public void update(UserDTO userDTO) throws ServiceException {
        validateUser(userDTO);
        User user = convertDTOToUser(userDTO);
        try {
            userDAO.update(user);
        } catch (DAOException e) {
            checkExceptionType(e);
        }
    }

    @Override
    public void changePassword(long userId, String oldPassword, String newPassword, String confirmPassword)
            throws ServiceException {
        checkStrings(oldPassword, newPassword, confirmPassword);
        try {
            User user = userDAO.getById(userId).orElseThrow(NoSuchUserException::new);
            verify(user.getPassword(), oldPassword);
            checkPasswordMatching(newPassword, confirmPassword);
            validatePassword(newPassword);
            User userToUpdate = new User.Builder().setId(userId).setPassword(encode(newPassword)).get();
            userDAO.updatePassword(userToUpdate);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void setRole(String userEmail, int roleId) throws ServiceException {
        try {
            Role role = Role.getRole(roleId);
            userDAO.setUserRole(userEmail, role);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void delete(String userIdString) throws ServiceException {
        long userId = getUserId(userIdString);
        try {
            userDAO.delete(userId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void registerForEvent(long userId, String eventIdString) throws ServiceException {
        try {
            long eventId = getLong(eventIdString);
            userDAO.registerForEvent(userId, eventId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void cancelRegistration(long userId, String eventIdString) throws ServiceException {
        try {
            long eventId = getLong(eventIdString);
            userDAO.cancelRegistration(userId, eventId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean isRegistered(long userId, String eventIdString) throws ServiceException {
        boolean result;
        try {
            long eventId = getLong(eventIdString);
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

    private void validateUser(UserDTO userDTO) throws IncorrectFormatException {
        validateEmail(userDTO.getEmail());
        if (userDTO.getPassword() != null) {
            validatePassword(userDTO.getPassword());
        }
        validateName(userDTO.getName(), ENTER_CORRECT_NAME);
        validateName(userDTO.getSurname(), ENTER_CORRECT_SURNAME);
    }
}