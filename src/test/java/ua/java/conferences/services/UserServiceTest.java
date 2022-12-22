package ua.java.conferences.services;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import ua.java.conferences.model.dao.UserDAO;
import ua.java.conferences.dto.UserDTO;
import ua.java.conferences.model.entities.User;
import ua.java.conferences.model.entities.role.Role;
import ua.java.conferences.exceptions.*;
import ua.java.conferences.model.services.UserService;
import ua.java.conferences.model.services.implementation.UserServiceImpl;

import java.sql.SQLException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static ua.java.conferences.Constants.*;
import static ua.java.conferences.model.utils.PasswordHashUtil.*;
import static ua.java.conferences.exceptions.constants.Message.*;
import static ua.java.conferences.model.utils.QueryBuilderUtil.*;

class UserServiceTest {
    private final UserDAO userDAO = mock(UserDAO.class);
    private final UserService userService = new UserServiceImpl(userDAO);
    private final Long ONE = 1L;

    @Test
    void testCorrectRegistration() throws DAOException {
       doNothing().when(userDAO).add(isA(User.class));
       UserDTO userDTO = getTestUserDTO();
       assertDoesNotThrow(() -> userService.add(userDTO, PASSWORD, PASSWORD));
    }

    @Test
    void testWrongEmailRegistration() throws DAOException {
        doNothing().when(userDAO).add(isA(User.class));
        UserDTO userDTO = getTestUserDTO();
        userDTO.setEmail(INCORRECT_EMAIL);
        IncorrectFormatException e = assertThrows(IncorrectFormatException.class ,
                () -> userService.add(userDTO, PASSWORD, PASSWORD));
        assertEquals(ENTER_CORRECT_EMAIL, e.getMessage());
    }

    @Test
    void testWrongPassRegistration() throws DAOException {
        doNothing().when(userDAO).add(isA(User.class));
        UserDTO userDTO = getTestUserDTO();
        IncorrectFormatException e = assertThrows(IncorrectFormatException.class ,
                () -> userService.add(userDTO, INCORRECT_PASSWORD, INCORRECT_PASSWORD));
        assertEquals(ENTER_CORRECT_PASSWORD, e.getMessage());
    }

    @Test
    void testWrongNameRegistration() throws DAOException {
        doNothing().when(userDAO).add(isA(User.class));
        UserDTO userDTO = getTestUserDTO();
        userDTO.setName(INCORRECT_NAME);
        IncorrectFormatException e = assertThrows(IncorrectFormatException.class ,
                () -> userService.add(userDTO, PASSWORD, PASSWORD));
        assertEquals(ENTER_CORRECT_NAME, e.getMessage());
    }

    @Test
    void testWrongSurnameRegistration() throws DAOException {
        doNothing().when(userDAO).add(isA(User.class));
        UserDTO userDTO = getTestUserDTO();
        userDTO.setSurname(INCORRECT_SURNAME);
        IncorrectFormatException e = assertThrows(IncorrectFormatException.class ,
                () -> userService.add(userDTO, PASSWORD, PASSWORD));
        assertEquals(ENTER_CORRECT_SURNAME, e.getMessage());
    }

    @Test
    void testDuplicateEmailRegistration() throws DAOException {
        doThrow(new DAOException(new SQLException("Duplicate entry"))).when(userDAO).add(isA(User.class));
        UserDTO userDTO = getTestUserDTO();
        DuplicateEmailException e = assertThrows(DuplicateEmailException.class ,
                () -> userService.add(userDTO, PASSWORD, PASSWORD));
        assertEquals(DUPLICATE_EMAIL, e.getMessage());
    }

    @Test
    void testPasswordsDoNotMatch() throws DAOException {
        doNothing().when(userDAO).add(isA(User.class));
        UserDTO userDTO = getTestUserDTO();
        PasswordMatchingException e = assertThrows(PasswordMatchingException.class ,
                () -> userService.add(userDTO, PASSWORD, INCORRECT_PASSWORD));
        assertEquals(PASSWORD_MATCHING, e.getMessage());
    }

    @Test
    void testDbIsDown() throws DAOException {
        Exception exception = new DAOException(new SQLException());
        doThrow(exception).when(userDAO).add(isA(User.class));
        UserDTO userDTO = getTestUserDTO();
        ServiceException e = assertThrows(ServiceException.class ,
                () -> userService.add(userDTO, PASSWORD, PASSWORD));
        assertEquals(e.getCause(), exception);
        assertFalse(e.getMessage().contains("Duplicate entry"));
    }


    @Test
    void testSignIn() throws DAOException, ServiceException {
        User user = getTestUser();
        user.setPassword(encode(PASSWORD));
        when(userDAO.getByEmail(EMAIL)).thenReturn(Optional.of(user));
        assertEquals(getTestUserDTO(), userService.signIn(EMAIL, PASSWORD));
    }

    @Test
    void testWrongEmailSignIn() throws DAOException {
        when(userDAO.getByEmail(EMAIL)).thenReturn(Optional.empty());
        assertThrows(NoSuchUserException.class,() -> userService.signIn(EMAIL, PASSWORD));
    }

    @Test
    void testWrongPasswordSignIn() throws DAOException {
        User testUser = getTestUser();
        testUser.setPassword(encode(PASSWORD));
        when(userDAO.getByEmail(EMAIL)).thenReturn(Optional.of(testUser));
        assertThrows(IncorrectPasswordException.class,() -> userService.signIn(EMAIL, INCORRECT_PASSWORD));
    }

    @Test
    void testViewProfile() throws DAOException, ServiceException {
        when(userDAO.getById(ONE)).thenReturn(Optional.of(getTestUser()));
        assertEquals(getTestUserDTO(), userService.getById(String.valueOf(ONE)));
    }

    @Test
    void testViewProfileNoUser() throws DAOException {
        when(userDAO.getById(ONE)).thenReturn(Optional.empty());
        assertThrows(NoSuchUserException.class,() -> userService.getById(String.valueOf(ONE)));
    }

    @Test
    void testSearchUser() throws DAOException, ServiceException {
        when(userDAO.getByEmail(EMAIL)).thenReturn(Optional.of(getTestUser()));
        assertEquals(getTestUserDTO(), userService.getByEmail(EMAIL));
    }

    @Test
    void testSearchNoUser() throws DAOException {
        when(userDAO.getByEmail(EMAIL)).thenReturn(Optional.empty());
        assertThrows(NoSuchUserException.class,() -> userService.getByEmail(EMAIL));
    }

    @Test
    void testViewUsers() throws DAOException, ServiceException {
        List<User> users = new ArrayList<>();
        List<UserDTO> userDTOs = new ArrayList<>();
        users.add(getTestUser());
        userDTOs.add(getTestUserDTO());
        when(userDAO.getAll()).thenReturn(users);
        assertIterableEquals(userDTOs, userService.getAll());
    }

    @Test
    void testViewSortedUsers() throws DAOException, ServiceException {
        List<User> users = new ArrayList<>();
        List<UserDTO> userDTOs = new ArrayList<>();
        users.add(getTestUser());
        userDTOs.add(getTestUserDTO());
        String query = userQueryBuilder().getQuery();
        when(userDAO.getSorted(query)).thenReturn(users);
        assertIterableEquals(userDTOs, userService.getSortedUsers(query));
    }

    @Test
    void testNumberOfRecords() throws DAOException, ServiceException {
        String filter = userQueryBuilder().getRecordQuery();
        when(userDAO.getNumberOfRecords(filter)).thenReturn(1);
        assertEquals(1, userService.getNumberOfRecords(filter));
    }

    @Test
    void testViewSpeakers() throws DAOException, ServiceException {
        List<User> users = new ArrayList<>();
        List<UserDTO> userDTOs = new ArrayList<>();
        users.add(getTestUser());
        userDTOs.add(getTestUserDTO());
        String query = userQueryBuilder().setRoleFilter("3").getQuery();
        when(userDAO.getSorted(query)).thenReturn(users);
        assertIterableEquals(userDTOs, userService.getSpeakers());
    }

    @Test
    void testEditProfile() throws DAOException {
        doNothing().when(userDAO).update(isA(User.class));
        assertDoesNotThrow(() -> userService.update(getTestUserDTO()));
    }

    @Test
    void testWrongEmailEditProfile() throws DAOException {
        doNothing().when(userDAO).update(isA(User.class));
        UserDTO userDTO = getTestUserDTO();
        userDTO.setEmail(INCORRECT_EMAIL);
        IncorrectFormatException e = assertThrows(IncorrectFormatException.class ,
                () -> userService.update(userDTO));
        assertEquals(ENTER_CORRECT_EMAIL, e.getMessage());
    }

    @Test
    void testWrongPassEditProfile() throws DAOException {
        doNothing().when(userDAO).update(isA(User.class));
        assertDoesNotThrow(() -> userService.update(getTestUserDTO()));
    }

    @Test
    void testWrongNameEditProfile() throws DAOException {
        doNothing().when(userDAO).update(isA(User.class));
        UserDTO userDTO = getTestUserDTO();
        userDTO.setName(INCORRECT_NAME);
        IncorrectFormatException e = assertThrows(IncorrectFormatException.class ,
                () -> userService.update(userDTO));
        assertEquals(ENTER_CORRECT_NAME, e.getMessage());
    }

    @Test
    void testWrongSurnameEditProfile() throws DAOException {
        doNothing().when(userDAO).update(isA(User.class));
        UserDTO userDTO = getTestUserDTO();
        userDTO.setSurname(INCORRECT_SURNAME);
        IncorrectFormatException e = assertThrows(IncorrectFormatException.class ,
                () -> userService.update(userDTO));
        assertEquals(ENTER_CORRECT_SURNAME, e.getMessage());
    }

    @Test
    void testDuplicateEmailEditProfile() throws DAOException {
        doThrow(new DAOException(new SQLException("Duplicate entry"))).when(userDAO).update(isA(User.class));
        UserDTO userDTO = getTestUserDTO();
        DuplicateEmailException e = assertThrows(DuplicateEmailException.class ,
                () -> userService.update(userDTO));
        assertEquals(DUPLICATE_EMAIL, e.getMessage());
    }

    @Test
    void testUpdatePassword() throws DAOException {
        doNothing().when(userDAO).updatePassword(isA(User.class));
        User testUser = getTestUser();
        testUser.setPassword(encode(PASSWORD));
        when(userDAO.getById(ONE)).thenReturn(Optional.of(testUser));
        assertDoesNotThrow(() -> userService.changePassword(ONE, PASSWORD, PASSWORD, PASSWORD));
    }

    @Test
    void testEditWrongNewPassword() throws DAOException {
        doNothing().when(userDAO).updatePassword(isA(User.class));
        User testUser = getTestUser();
        testUser.setPassword(encode(PASSWORD));
        when(userDAO.getById(ONE)).thenReturn(Optional.of(testUser));
        assertThrows(IncorrectFormatException.class,
                () -> userService.changePassword(ONE, PASSWORD, WRONG_PASSWORD, WRONG_PASSWORD));
    }

    @Test
    void testEditWrongOldPassword() throws DAOException {
        doNothing().when(userDAO).updatePassword(isA(User.class));
        User testUser = getTestUser();
        testUser.setPassword(encode(PASSWORD));
        when(userDAO.getById(ONE)).thenReturn(Optional.of(testUser));
        assertThrows(IncorrectPasswordException.class,
                () -> userService.changePassword(ONE, WRONG_PASSWORD, PASSWORD, PASSWORD));
    }

    @Test
    void testEditWrongConfirmPassword() throws DAOException {
        doNothing().when(userDAO).updatePassword(isA(User.class));
        User testUser = getTestUser();
        testUser.setPassword(encode(PASSWORD));
        when(userDAO.getById(ONE)).thenReturn(Optional.of(testUser));
        assertThrows(PasswordMatchingException.class,
                () -> userService.changePassword(ONE, PASSWORD, PASSWORD, WRONG_PASSWORD));
    }

    @Test
    void testSetRole() throws DAOException {
        doNothing().when(userDAO).setUserRole(isA(String.class), isA(Role.class));
        assertDoesNotThrow(() -> userService.setRole(EMAIL, ROLE_ID));
    }

    @Test
    void testDeleteUser() throws DAOException {
        doNothing().when(userDAO).delete(isA(long.class));
        assertDoesNotThrow(() -> userService.delete(String.valueOf(ONE)));
    }

    @Test
    void testRegisterForEvent() throws DAOException {
        doNothing().when(userDAO).registerForEvent(isA(long.class), isA(long.class));
        assertDoesNotThrow(() -> userService.registerForEvent(ONE, String.valueOf(ONE)));
    }

    @Test
    void testCancelRegistration() throws DAOException {
        doNothing().when(userDAO).cancelRegistration(isA(long.class), isA(long.class));
        assertDoesNotThrow(() -> userService.cancelRegistration(ONE, String.valueOf(ONE)));
    }

    @Test
    void TestIsRegistered() throws DAOException, ServiceException {
        when(userDAO.isRegistered(ONE, ONE)).thenReturn(true);
        assertTrue(userService.isRegistered(ONE, String.valueOf(ONE)));
    }

    @Test
    void TestIsNotRegistered() throws DAOException, ServiceException {
        when(userDAO.isRegistered(ONE, ONE)).thenReturn(false);
        assertFalse(userService.isRegistered(ONE, String.valueOf(ONE)));
    }

    @Test
    void testServiceException() {
        assertNotEquals(new ServiceException(), new ServiceException());
    }

    private UserDTO getTestUserDTO() {
        return UserDTO.builder()
                .id(ONE)
                .email(EMAIL)
                .name(NAME)
                .surname(SURNAME)
                .notification(NOTIFICATION)
                .role(ROLE_VISITOR)
                .build();
    }

    private User getTestUser() {
        return User.builder()
                .id(ONE)
                .email(EMAIL)
                .password(PASSWORD)
                .name(NAME)
                .surname(SURNAME)
                .roleId(4)
                .build();
    }
}