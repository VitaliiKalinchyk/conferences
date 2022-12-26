package ua.java.conferences.model.services.implementation;

import org.apache.commons.lang3.RandomStringUtils;
import ua.java.conferences.dto.UserDTO;
import ua.java.conferences.model.dao.UserDAO;
import ua.java.conferences.model.entities.User;
import ua.java.conferences.model.entities.role.Role;
import ua.java.conferences.exceptions.*;
import ua.java.conferences.model.services.UserService;

import java.util.*;

import static ua.java.conferences.exceptions.constants.Message.*;
import static ua.java.conferences.utils.ConvertorUtil.*;
import static ua.java.conferences.utils.PasswordHashUtil.*;
import static ua.java.conferences.utils.ValidatorUtil.*;
import static ua.java.conferences.utils.QueryBuilderUtil.*;

public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;

    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public void add(UserDTO userDTO, String password, String confirmPassword) throws ServiceException {
        validateUser(userDTO);
        validatePassword(password);
        checkPasswordMatching(password, confirmPassword);
        User user = convertDTOToUser(userDTO);
        user.setPassword(encode(password));
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
    public List<UserDTO> getSortedUsers(String query) throws ServiceException {
        List<UserDTO> userDTOS = new ArrayList<>();
        try {
            List<User> users = userDAO.getSorted(query);
            users.forEach(user -> userDTOS.add(convertUserToDTO(user)));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return userDTOS;
    }

    @Override
    public List<UserDTO> getParticipants(String eventId, Role role) throws ServiceException {
        List<UserDTO> userDTOS = new ArrayList<>();
        try {
            List<User> users = userDAO.getParticipants(Long.parseLong(eventId), role);
            users.forEach(user -> userDTOS.add(convertUserToDTO(user)));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return userDTOS;
    }

    @Override
    public int getNumberOfRecords(String filter) throws ServiceException {
        int records;
        try {
            records = userDAO.getNumberOfRecords(filter);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return records;
    }

    @Override
    public List<UserDTO> getSpeakers() throws ServiceException {
        List<UserDTO> userDTOS = new ArrayList<>();
        try {
            List<User> speakers = userDAO.getSorted(userQueryBuilder().setRoleFilter("3").getQuery());
            speakers.forEach(user -> userDTOS.add(convertUserToDTO(user)));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return userDTOS;
    }

    @Override
    public List<UserDTO> getModerators() throws ServiceException {
        List<UserDTO> userDTOS = new ArrayList<>();
        try {
            List<User> moderators = userDAO.getSorted(userQueryBuilder().setRoleFilter("2").getQuery());
            moderators.forEach(user -> userDTOS.add(convertUserToDTO(user)));
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
    public void changePassword(long userId, String password, String newPass, String confirmPass)
            throws ServiceException {
        checkStrings(password, newPass, confirmPass);
        try {
            User user = userDAO.getById(userId).orElseThrow(NoSuchUserException::new);
            verify(user.getPassword(), password);
            checkPasswordMatching(newPass, confirmPass);
            validatePassword(newPass);
            User userToUpdate = User.builder().id(userId).password(encode(newPass)).build();
            userDAO.updatePassword(userToUpdate);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public String changePassword(long userId) throws ServiceException {
        String newPass = generatePassword();
        try {
            User user = User.builder().id(userId).password(encode(newPass)).build();
            userDAO.updatePassword(user);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return newPass;
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

    private void validateUser(UserDTO userDTO) throws IncorrectFormatException {
        validateEmail(userDTO.getEmail());
        validateName(userDTO.getName(), ENTER_CORRECT_NAME);
        validateName(userDTO.getSurname(), ENTER_CORRECT_SURNAME);
    }

    private String generatePassword() {
        return "1aA" + RandomStringUtils.randomAlphanumeric(5);
    }
}