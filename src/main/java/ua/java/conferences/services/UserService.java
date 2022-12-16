package ua.java.conferences.services;

import ua.java.conferences.dto.UserDTO;
import ua.java.conferences.exceptions.*;
import ua.java.conferences.utils.sorting.Sorting;

import java.util.List;

public interface UserService extends Service<UserDTO> {
    void add(UserDTO userDTO, String confirmPassword) throws ServiceException;

    UserDTO signIn(String login, String password) throws ServiceException;

    UserDTO getByEmail(String email) throws ServiceException;

    List<UserDTO> getSortedUsers(Sorting sorting, String offset, String records) throws ServiceException;

    int getNumberOfRecords(Sorting sorting) throws ServiceException;

    List<UserDTO> getSpeakers() throws ServiceException;

    void changePassword(long userId, String oldPassword, String newPassword, String confirmPassword) throws ServiceException;

    void setRole(String userEmail, int roleId) throws ServiceException;

    void registerForEvent(long userId, String eventIdString) throws ServiceException;

    void cancelRegistration(long userId, String eventIdString) throws ServiceException;

    boolean isRegistered(long userIdString, String eventIdString) throws ServiceException;
}