package ua.java.conferences.dao;

import org.junit.jupiter.api.*;
import ua.java.conferences.entities.*;
import ua.java.conferences.entities.role.Role;
import ua.java.conferences.exceptions.DAOException;

import java.io.*;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ua.java.conferences.dao.DAOTestUtils.*;

class UserDAOTest {

    @BeforeEach
    void clearDB() throws FileNotFoundException, SQLException {
        createEmptyDB();
    }

    @Test
    void testCrud() throws DAOException {
        User testUser = getTestUser();
        assertDoesNotThrow(() -> userDAO.add(testUser));

        User resultUser = userDAO.getByEmail(testUser.getEmail()).orElse(null);
        assertNotNull(resultUser);
        assertNotEquals(0, resultUser.getId());
        assertNotEquals(resultUser, testUser);
        assertEquals(resultUser.getEmail(), testUser.getEmail());
        assertEquals(resultUser.getPassword(), testUser.getPassword());
        assertEquals(resultUser.getName(), testUser.getName());
        assertEquals(resultUser.getSurname(), testUser.getSurname());
        assertEquals(resultUser.isEmailNotification(), testUser.isEmailNotification());
        assertEquals(Role.VISITOR.getValue(), resultUser.getRoleId());

        List<User> users = userDAO.getAll();
        assertTrue(users.contains(resultUser));
        assertEquals(1, users.size());

        resultUser.setName("Result");
        assertDoesNotThrow(() -> userDAO.update(resultUser));

        User updatedUser = userDAO.getById(resultUser.getId()).orElse(null);
        assertNotNull(updatedUser);
        assertEquals("Result", updatedUser.getName());

        assertDoesNotThrow(() -> userDAO.delete(updatedUser.getId()));
        users = userDAO.getAll();
        assertEquals(0, users.size());
    }

    @Test
    void testAddTwice() {
        assertDoesNotThrow(() -> userDAO.add(getTestUser()));
        DAOException exception = assertThrows((DAOException.class), () -> userDAO.add(getTestUser()));
        assertTrue(exception.getMessage().contains("Duplicate entry"));
    }

    @Test
    void testGetAbsent() throws DAOException {
        assertNull(userDAO.getByEmail(getTestUser().getEmail()).orElse(null));
    }

    @Test
    void testUpdateEmail() throws DAOException {
        userDAO.add(getTestUser());
        User testUser = userDAO.getById(1).orElse(null);
        assertNotNull(testUser);

        testUser.setEmail("newEmail@email.com");
        userDAO.updateEmail(testUser);
        testUser = userDAO.getById(1).orElse(null);
        assertNotNull(testUser);

        assertEquals("newEmail@email.com", testUser.getEmail());
    }

    @Test
    void testUpdateSameEmail() throws DAOException {
        userDAO.add(getTestUser());
        User secondUser = getTestUser();
        secondUser.setEmail("someEmail");
        userDAO.add(secondUser);
        secondUser.setEmail(getTestUser().getEmail());
        secondUser.setId(2);
        DAOException exception = assertThrows((DAOException.class), () -> userDAO.updateEmail(secondUser));
        assertTrue(exception.getMessage().contains("Duplicate entry"));
    }

    @Test
    void testUpdatePassword() throws DAOException {
        userDAO.add(getTestUser());
        User testUser = userDAO.getById(1).orElse(null);
        assertNotNull(testUser);

        testUser.setPassword("newPassword1234");
        userDAO.updatePassword(testUser);
        testUser = userDAO.getById(1).orElse(null);
        assertNotNull(testUser);

        assertEquals("newPassword1234", testUser.getPassword());
    }

    @Test
    void testRegisterForEvent() throws DAOException {
        User testUser = getTestUser();
        userDAO.add(testUser);
        Event testEvent = getTestEvent();
        eventDAO.add(testEvent);
        assertDoesNotThrow(() -> userDAO.registerForEvent(1, 1));

        assertTrue(userDAO.isRegistered(1, 1));

        List<Event> events = eventDAO.getEventsByVisitor(1);
        assertEquals(1, events.size());
    }

    @Test
    void testBadRegistrations() throws DAOException {
        User testUser = getTestUser();
        userDAO.add(testUser);
        assertThrows((DAOException.class), () -> userDAO.registerForEvent(1, 1));
        Event testEvent = getTestEvent();
        eventDAO.add(testEvent);

        assertFalse(userDAO.isRegistered(1, 1));

        List<Event> events = eventDAO.getEventsByVisitor(1);
        assertEquals(0, events.size());
    }

    @Test
    void testRoleMethods() throws DAOException {
        User testUser = getTestUser();
        userDAO.add(testUser);
        testUser = userDAO.getById(1).orElse(null);
        assertNotNull(testUser);
        assertEquals(Role.VISITOR.getValue(), testUser.getRoleId());

        userDAO.setUsersRole(1, Role.ADMIN);
        testUser = userDAO.getById(1).orElse(null);
        assertNotNull(testUser);
        assertEquals(Role.ADMIN.getValue(), testUser.getRoleId());
    }

    @Test
    void testSpeakersMethod() throws DAOException {
        User testUser = getTestUser();
        userDAO.add(testUser);
        userDAO.setUsersRole(1, Role.MODERATOR);
        List<User> users = userDAO.getSpeakers();
        assertEquals(0, users.size());

        userDAO.setUsersRole(1, Role.SPEAKER);
        users = userDAO.getSpeakers();
        assertEquals(1, users.size());
    }
}