package ua.java.conferences.services.implementation;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import ua.java.conferences.dao.UserDAO;
import ua.java.conferences.dto.request.UserRequestDTO;
import ua.java.conferences.dto.response.UserResponseDTO;
import ua.java.conferences.entities.User;
import ua.java.conferences.entities.role.Role;
import ua.java.conferences.exceptions.*;
import ua.java.conferences.services.UserService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static ua.java.conferences.Constants.*;
import static ua.java.conferences.utils.PasswordHashUtil.encode;

class UserServiceImplTest {

    private final UserDAO userDAO = mock(UserDAO.class);

    private final UserService userService = new UserServiceImpl(userDAO);

    @Test
    void testCorrectRegistration() throws DAOException {
       doNothing().when(userDAO).add(isA(User.class));
       UserRequestDTO userDTO = getTestUserRequestDTO();
       assertDoesNotThrow(() -> userService.register(userDTO));
    }

    @Test
    void testWrongEmailRegistration() throws DAOException {
        doNothing().when(userDAO).add(isA(User.class));
        UserRequestDTO userDTO = new UserRequestDTO(ID, WRONG_EMAIL, PASSWORD, NAME, SURNAME, NOTIFICATION);
        IncorrectFormatException e = assertThrows(IncorrectFormatException.class , () -> userService.register(userDTO));
        assertEquals("email", e.getMessage());
    }

    @Test
    void testWrongPassRegistration() throws DAOException {
        doNothing().when(userDAO).add(isA(User.class));
        UserRequestDTO userDTO = new UserRequestDTO(ID, EMAIL, WRONG_PASSWORD, NAME, SURNAME, NOTIFICATION);
        IncorrectFormatException e = assertThrows(IncorrectFormatException.class , () -> userService.register(userDTO));
        assertEquals("password", e.getMessage());
    }

    @Test
    void testWrongNameRegistration() throws DAOException {
        doNothing().when(userDAO).add(isA(User.class));
        UserRequestDTO userDTO = new UserRequestDTO(ID, EMAIL, PASSWORD, WRONG_NAME, SURNAME, NOTIFICATION);
        IncorrectFormatException e = assertThrows(IncorrectFormatException.class , () -> userService.register(userDTO));
        assertEquals("name", e.getMessage());
    }

    @Test
    void testWrongSurnameRegistration() throws DAOException {
        doNothing().when(userDAO).add(isA(User.class));
        UserRequestDTO userDTO = new UserRequestDTO(ID, EMAIL, PASSWORD, NAME, WRONG_SURNAME, NOTIFICATION);
        IncorrectFormatException e = assertThrows(IncorrectFormatException.class , () -> userService.register(userDTO));
        assertEquals("surname", e.getMessage());
    }

    @Test
    void testDuplicateEmailRegistration() throws DAOException {
        doThrow(new DAOException(new SQLException("Duplicate entry"))).when(userDAO).add(isA(User.class));
        UserRequestDTO userDTO = new UserRequestDTO(ID, EMAIL, PASSWORD, NAME, SURNAME, NOTIFICATION);
        ServiceException e = assertThrows(ServiceException.class , () -> userService.register(userDTO));
        assertTrue(e.getMessage().contains("Duplicate entry"));
    }

    @Test
    void testDbIsDown() throws DAOException {
        doThrow(new DAOException(new SQLException())).when(userDAO).add(isA(User.class));
        UserRequestDTO userDTO = new UserRequestDTO(ID, EMAIL, PASSWORD, NAME, SURNAME, NOTIFICATION);
        ServiceException e = assertThrows(ServiceException.class , () -> userService.register(userDTO));
        assertFalse(e.getMessage().contains("Duplicate entry"));
    }

    @Test
    void testSignIn() throws DAOException, ServiceException {
        User user = getTestUser();
        user.setPassword(encode(PASSWORD));
        when(userDAO.getByEmail(EMAIL)).thenReturn(Optional.of(user));
        assertEquals(getTestUserResponseDTO(), userService.signIn(EMAIL, PASSWORD));
    }

    @Test
    void testWrongEmailSignIn() throws DAOException {
        when(userDAO.getByEmail(EMAIL)).thenReturn(Optional.empty());
        assertThrows(IncorrectEmailException.class,() -> userService.signIn(EMAIL, PASSWORD));
    }

    @Test
    void testWrongPasswordSignIn() throws DAOException {
        when(userDAO.getByEmail(EMAIL)).thenReturn(Optional.of(getTestUser()));
        assertThrows(IncorrectPasswordException.class,() -> userService.signIn(EMAIL, PASSWORD));
    }

    @Test
    void testViewProfile() throws DAOException, ServiceException {
        when(userDAO.getById(ID)).thenReturn(Optional.of(getTestUser()));
        assertEquals(getTestUserResponseDTO(), userService.viewProfile(ID));
    }

    @Test
    void testViewProfileNoUser() throws DAOException {
        when(userDAO.getById(ID)).thenReturn(Optional.empty());
        assertThrows(NoSuchUserException.class,() -> userService.viewProfile(ID));
    }

    @Test
    void testSearchUser() throws DAOException, ServiceException {
        when(userDAO.getByEmail(EMAIL)).thenReturn(Optional.of(getTestUser()));
        assertEquals(getTestUserResponseDTO(), userService.searchUser(EMAIL));
    }

    @Test
    void testSearchNoUser() throws DAOException {
        when(userDAO.getByEmail(EMAIL)).thenReturn(Optional.empty());
        assertThrows(NoSuchUserException.class,() -> userService.searchUser(EMAIL));
    }

    @Test
    void testViewUsers() throws DAOException, ServiceException {
        List<User> users = new ArrayList<>();
        List<UserResponseDTO> userDTOs = new ArrayList<>();
        users.add(getTestUser());
        userDTOs.add(getTestUserResponseDTO());
        when(userDAO.getAll()).thenReturn(users);
        assertIterableEquals(userDTOs, userService.viewUsers());
    }

    @Test
    void testViewSpeakers() throws DAOException, ServiceException {
        List<User> users = new ArrayList<>();
        List<UserResponseDTO> userDTOs = new ArrayList<>();
        users.add(getTestUser());
        userDTOs.add(getTestUserResponseDTO());
        when(userDAO.getSpeakers()).thenReturn(users);
        assertIterableEquals(userDTOs, userService.viewSpeakers());
    }

    @Test
    void testEditProfile() throws DAOException, ServiceException {
        doNothing().when(userDAO).update(isA(User.class));
        assertEquals(getTestUserResponseDTO(), userService.editProfile(getTestUserRequestDTO()));
    }

    @Test
    void testSetRole() throws DAOException {
        doNothing().when(userDAO).setUsersRole(isA(long.class), isA(Role.class));
        assertDoesNotThrow(() -> userService.setRole(ID, ROLE_ID));
    }

    @Test
    void testDeleteUser() throws DAOException {
        doNothing().when(userDAO).delete(isA(long.class));
        assertDoesNotThrow(() -> userService.deleteUser(ID));
    }

    @Test
    void testRegisterForEvent() throws DAOException {
        doNothing().when(userDAO).registerForEvent(isA(long.class), isA(long.class));
        assertDoesNotThrow(() -> userService.registerForEvent(ID, ID));
    }

    @Test
    void TestIsRegistered() throws DAOException, ServiceException {
        when(userDAO.isRegistered(ID, ID)).thenReturn(true);
        assertTrue(userService.isRegistered(ID, ID));
    }

    @Test
    void TestIsNotRegistered() throws DAOException, ServiceException {
        when(userDAO.isRegistered(ID, ID)).thenReturn(false);
        assertFalse(userService.isRegistered(ID, ID));
    }

    private UserRequestDTO getTestUserRequestDTO() {
        return new UserRequestDTO(ID, EMAIL, PASSWORD, NAME, SURNAME, NOTIFICATION);
    }
    private UserResponseDTO getTestUserResponseDTO() {
        return new UserResponseDTO(ID, EMAIL, NAME, SURNAME, NOTIFICATION, ROLE_NAME);
    }

    private User getTestUser() {
        return new User.UserBuilder()
                .setId(ID)
                .setEmail(EMAIL)
                .setPassword(PASSWORD)
                .setName(NAME)
                .setSurname(SURNAME)
                .get();
    }
}