package ua.java.conferences.dao.mysql;

import org.junit.jupiter.api.*;
import ua.java.conferences.entity.*;
import ua.java.conferences.entity.role.Role;
import ua.java.conferences.exception.DAOException;

import java.io.*;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ua.java.conferences.dao.mysql.DAOTestUtils.*;

class MysqlUserDAOTest {

    @BeforeEach
    void clearDB() throws FileNotFoundException, SQLException {
        createEmptyDB();
    }

    @Test
    void testCrud() throws DAOException {
        User testUser = getTestUser();
        assertTrue(userDAO.add(testUser));

        User resultUser = userDAO.getById(testUser.getId());
        assertNotEquals(0, resultUser.getId());
        assertEquals(resultUser, testUser);
        assertEquals(resultUser.getId(), testUser.getId());
        assertEquals(resultUser.getEmail(), testUser.getEmail());
        assertEquals(resultUser.getPassword(), testUser.getPassword());
        assertEquals(resultUser.getName(), testUser.getName());
        assertEquals(resultUser.getSurname(), testUser.getSurname());
        assertEquals(resultUser.isEmailNotification(), testUser.isEmailNotification());

        List<User> users = userDAO.getAll();
        assertTrue(users.contains(resultUser));
        assertEquals(1, users.size());

        resultUser.setName("Result");
        assertTrue(userDAO.update(resultUser));

        User changedUser = userDAO.getByEmail(resultUser.getEmail());
        assertEquals("Result", changedUser.getName());
        assertEquals(resultUser, changedUser);

        assertTrue(userDAO.delete(resultUser.getId()));
        users = userDAO.getAll();
        assertEquals(0, users.size());
    }

    @Test
    void testAddTwice() throws DAOException {
        assertTrue(userDAO.add(getTestUser()));
        assertFalse(userDAO.add(getTestUser()));
    }

    @Test
    void testGetAbsent() throws DAOException {
        assertNull(userDAO.getByEmail(getTestUser().getEmail()));
    }

    @Test
    void testUpdateAbsent() throws DAOException {
        assertFalse(userDAO.update(getTestUser()));
    }

    @Test
    void testDeleteAbsent() throws DAOException {
        assertFalse(userDAO.delete(getTestUser().getId()));
    }

    @Test
    void testRegisterForEvent() throws DAOException {
        User testUser = getTestUser();
        userDAO.add(testUser);
        Event testEvent = getTestEvent();
        eventDAO.add(testEvent);
        assertTrue(userDAO.registerForEvent(testUser.getId(), testEvent.getId()));

        List<User> users = userDAO.getUsersByEvent(testEvent.getId());
        assertTrue(users.contains(testUser));
        assertEquals(1, users.size());
    }

    @Test
    void testBadRegistrations() throws DAOException {
        User testUser = getTestUser();
        userDAO.add(testUser);
        Event testEvent = getTestEvent();
        assertFalse(userDAO.registerForEvent(testUser.getId(), testEvent.getId()));

        eventDAO.add(testEvent);
        assertTrue(userDAO.registerForEvent(testUser.getId(), testEvent.getId()));
        assertFalse(userDAO.registerForEvent(testUser.getId(), testEvent.getId()));

        List<User> users = userDAO.getUsersByEvent(testEvent.getId());
        assertTrue(users.contains(testUser));
        assertEquals(1, users.size());
    }

    @Test
    void testRoleMethods() throws DAOException {
        User testUser = getTestUser();
        userDAO.add(testUser);
        assertEquals(Role.VISITOR, userDAO.getUsersRole(testUser.getId()));
        assertTrue(userDAO.setUsersRole(testUser.getId(), Role.ADMIN));
        assertEquals(Role.ADMIN, userDAO.getUsersRole(testUser.getId()));

        List<User> usersByRole = userDAO.getUsersByRole(Role.MODERATOR);
        assertEquals(0, usersByRole.size());

        usersByRole = userDAO.getUsersByRole(Role.ADMIN);
        assertEquals(1, usersByRole.size());
        assertTrue(usersByRole.contains(testUser));
    }

    @Test
    void testSpeakerByReport() throws DAOException {
        User testUser = getTestUser();
        userDAO.add(testUser);
        Report testReport = getTestReport();
        reportDAO.add(testReport);
        reportDAO.setReportForSpeaker(testUser.getId(), testReport.getId());
        assertEquals(testUser, userDAO.getSpeakerByReport(testReport.getId()));
    }

    @Test
    void testNoReportForSpeaker() throws DAOException {
        Report testReport = getTestReport();
        assertNull(userDAO.getSpeakerByReport(testReport.getId()));
    }
}