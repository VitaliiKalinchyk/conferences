package ua.java.conferences.services;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import ua.java.conferences.dao.UserDAO;
import ua.java.conferences.dto.UserDTO;
import ua.java.conferences.entities.User;
import ua.java.conferences.entities.role.Role;
import ua.java.conferences.exceptions.*;
import ua.java.conferences.services.implementation.UserServiceImpl;
import ua.java.conferences.utils.sorting.Sorting;

import java.sql.SQLException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static ua.java.conferences.Constants.*;
import static ua.java.conferences.actions.constants.ParameterValues.ASCENDING_ORDER;
import static ua.java.conferences.actions.constants.Parameters.ID;
import static ua.java.conferences.utils.PasswordHashUtil.encode;
import static ua.java.conferences.exceptions.constants.Message.*;

class UserServiceTest {
    private final UserDAO userDAO = mock(UserDAO.class);
    private final UserService userService = new UserServiceImpl(userDAO);
    private final Long ONE = 1L;

    @Test
    void testCorrectRegistration() throws DAOException {
       doNothing().when(userDAO).add(isA(User.class));
       UserDTO userDTO = getTestUserDTO();
       assertDoesNotThrow(() -> userService.add(userDTO, PASSWORD));
    }

    @Test
    void testWrongEmailRegistration() throws DAOException {
        doNothing().when(userDAO).add(isA(User.class));
        UserDTO userDTO = getTestUserDTO();
        userDTO.setEmail(INCORRECT_EMAIL);
        IncorrectFormatException e = assertThrows(IncorrectFormatException.class ,
                () -> userService.add(userDTO, PASSWORD));
        assertEquals(ENTER_CORRECT_EMAIL, e.getMessage());
    }

    @Test
    void testWrongPassRegistration() throws DAOException {
        doNothing().when(userDAO).add(isA(User.class));
        UserDTO userDTO = getTestUserDTO();
        userDTO.setPassword(INCORRECT_PASSWORD);
        IncorrectFormatException e = assertThrows(IncorrectFormatException.class ,
                () -> userService.add(userDTO, PASSWORD));
        assertEquals(ENTER_CORRECT_PASSWORD, e.getMessage());
    }

    @Test
    void testWrongNameRegistration() throws DAOException {
        doNothing().when(userDAO).add(isA(User.class));
        UserDTO userDTO = getTestUserDTO();
        userDTO.setName(INCORRECT_NAME);
        IncorrectFormatException e = assertThrows(IncorrectFormatException.class ,
                () -> userService.add(userDTO, PASSWORD));
        assertEquals(ENTER_CORRECT_NAME, e.getMessage());
    }

    @Test
    void testWrongSurnameRegistration() throws DAOException {
        doNothing().when(userDAO).add(isA(User.class));
        UserDTO userDTO = getTestUserDTO();
        userDTO.setSurname(INCORRECT_SURNAME);
        IncorrectFormatException e = assertThrows(IncorrectFormatException.class ,
                () -> userService.add(userDTO, PASSWORD));
        assertEquals(ENTER_CORRECT_SURNAME, e.getMessage());
    }

    @Test
    void testDuplicateEmailRegistration() throws DAOException {
        doThrow(new DAOException(new SQLException("Duplicate entry"))).when(userDAO).add(isA(User.class));
        UserDTO userDTO = getTestUserDTO();
        DuplicateEmailException e = assertThrows(DuplicateEmailException.class ,
                () -> userService.add(userDTO, PASSWORD));
        assertEquals(DUPLICATE_EMAIL, e.getMessage());
    }

    @Test
    void testPasswordsDoNotMatch() throws DAOException {
        doNothing().when(userDAO).add(isA(User.class));
        UserDTO userDTO = getTestUserDTO();
        PasswordMatchingException e = assertThrows(PasswordMatchingException.class ,
                () -> userService.add(userDTO, INCORRECT_PASSWORD));
        assertEquals(PASSWORD_MATCHING, e.getMessage());
    }

    @Test
    void testDbIsDown() throws DAOException {
        Exception exception = new DAOException(new SQLException());
        doThrow(exception).when(userDAO).add(isA(User.class));
        UserDTO userDTO = getTestUserDTO();
        ServiceException e = assertThrows(ServiceException.class ,
                () -> userService.add(userDTO, PASSWORD));
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
        Sorting sorting = Sorting.getUserSorting(String.valueOf(Role.VISITOR.getValue()), ID, ASC);
        List<User> users = new ArrayList<>();
        List<UserDTO> userDTOs = new ArrayList<>();
        users.add(getTestUser());
        userDTOs.add(getTestUserDTO());
        System.out.println(users);
        System.out.println(userDTOs);
        when(userDAO.getSorted(sorting, ZERO, Integer.MAX_VALUE)).thenReturn(users);
        assertIterableEquals(userDTOs, userService.getSortedUsers(sorting, "0", String.valueOf(Integer.MAX_VALUE)));
    }

    @Test
    void testNumberOfRecords() throws DAOException, ServiceException {
        Sorting sorting = Sorting.getUserSorting(String.valueOf(Role.VISITOR.getValue()), ID, ASC);
        when(userDAO.getNumberOfRecords(sorting)).thenReturn(1);
        assertEquals(1, userService.getNumberOfRecords(sorting));
    }

    @Test
    void testViewSpeakers() throws DAOException, ServiceException {
        List<User> users = new ArrayList<>();
        List<UserDTO> userDTOs = new ArrayList<>();
        users.add(getTestUser());
        userDTOs.add(getTestUserDTO());
        Sorting sorting = Sorting.getUserSorting(String.valueOf(Role.SPEAKER.getValue()), ID, ASCENDING_ORDER);
        when(userDAO.getSorted(sorting, 0, Integer.MAX_VALUE)).thenReturn(users);
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
        UserDTO userDTO = getTestUserDTO();
        userDTO.setPassword(null);
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
        return new UserDTO.Builder()
                .setId(ONE)
                .setEmail(EMAIL)
                .setPassword(PASSWORD)
                .setName(NAME)
                .setSurname(SURNAME)
                .setNotification(NOTIFICATION)
                .setRole(ROLE_VISITOR)
                .get();
    }

    private User getTestUser() {
        return new User.Builder()
                .setId(ONE)
                .setEmail(EMAIL)
                .setPassword(PASSWORD)
                .setName(NAME)
                .setSurname(SURNAME)
                .setRoleId(4)
                .get();
    }
}