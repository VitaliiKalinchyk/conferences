package ua.java.conferences.model.services;

import ua.java.conferences.dto.UserDTO;
import ua.java.conferences.exceptions.*;
import ua.java.conferences.model.entities.role.Role;

import java.util.List;

public interface UserService extends Service<UserDTO> {
    void add(UserDTO userDTO, String password, String confirmPassword) throws ServiceException;

    UserDTO signIn(String login, String password) throws ServiceException;

    UserDTO getByEmail(String email) throws ServiceException;

    List<UserDTO> getSortedUsers(String query) throws ServiceException;

    List<UserDTO> getParticipants(String eventId, Role role) throws ServiceException;

    int getNumberOfRecords(String filter) throws ServiceException;

    List<UserDTO> getSpeakers() throws ServiceException;

    List<UserDTO> getModerators() throws ServiceException;

    void changePassword(long userId, String password, String newPass, String confirmPass) throws ServiceException;

    String changePassword(long userId) throws ServiceException;

    void setRole(String userEmail, int roleId) throws ServiceException;

    void registerForEvent(long userId, String eventIdString) throws ServiceException;

    void cancelRegistration(long userId, String eventIdString) throws ServiceException;

    boolean isRegistered(long userIdString, String eventIdString) throws ServiceException;
}