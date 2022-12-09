package ua.java.conferences.services;

import ua.java.conferences.dto.request.UserRequestDTO;
import ua.java.conferences.dto.response.UserResponseDTO;
import ua.java.conferences.exceptions.*;

import java.util.List;

public interface UserService extends Service<UserResponseDTO> {
    void register(UserRequestDTO userDTO, String confirmPassword) throws ServiceException;

    UserResponseDTO signIn(String login, String password) throws ServiceException;

    UserResponseDTO searchUser(String email) throws ServiceException;

    List<UserResponseDTO> viewUsers() throws ServiceException;

    List<UserResponseDTO> viewSpeakers() throws ServiceException;

    UserResponseDTO editProfile(UserRequestDTO userDTO) throws ServiceException;

    void changePassword(long userId, String oldPassword, String newPassword, String confirmPassword) throws ServiceException;

    void setRole(String userIdString, int roleId) throws ServiceException;

    void registerForEvent(long userId, String eventIdString) throws ServiceException;

    void cancelRegistration(long userId, String eventIdString) throws ServiceException;

    boolean isRegistered(long userIdString, String eventIdString) throws ServiceException;
}