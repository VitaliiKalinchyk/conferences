package ua.java.conferences.dao.mysql;

import org.junit.jupiter.api.*;
import ua.java.conferences.entity.*;
import ua.java.conferences.exception.DAOException;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ua.java.conferences.dao.mysql.DAOTestUtils.*;

class MysqlEventDAOTest {

    @BeforeEach
    void clearDB() throws FileNotFoundException, SQLException {
        createEmptyDB();
    }

    @Test
    void testCrud() throws DAOException {
        Event testEvent = getTestEvent();
        assertTrue(eventDAO.add(testEvent));

        Event resultEvent = eventDAO.getById(testEvent.getId());
        assertNotEquals(0, testEvent.getId());
        assertEquals(resultEvent, testEvent);
        assertEquals(resultEvent.getId(), testEvent.getId());
        assertEquals(resultEvent.getTitle(), testEvent.getTitle());
        assertEquals(resultEvent.getDate(), testEvent.getDate());
        assertEquals(resultEvent.getLocation(), testEvent.getLocation());
        assertEquals(resultEvent.getDescription(), testEvent.getDescription());
        assertEquals(resultEvent.getVisitors(), testEvent.getVisitors());

        List<Event> events = eventDAO.getAll();
        assertTrue(events.contains(resultEvent));
        assertEquals(1, events.size());

        resultEvent.setTitle("Result");
        assertTrue(eventDAO.update(resultEvent));
        Event changedEvent = eventDAO.getByTitle(resultEvent.getTitle());
        assertEquals("Result", changedEvent.getTitle());
        assertEquals(resultEvent, changedEvent);
        assertTrue(eventDAO.delete(resultEvent.getId()));

        events = eventDAO.getAll();
        assertEquals(0, events.size());
    }

    @Test
    void testAddTwice() throws DAOException {
        assertTrue(eventDAO.add(getTestEvent()));
        assertFalse(eventDAO.add(getTestEvent()));
    }

    @Test
    void testGetAbsent() {
        DAOException exception = assertThrows(DAOException.class, () -> eventDAO.getByTitle(getTestEvent().getTitle()));
        assertEquals("No such event", exception.getMessage());
    }

    @Test
    void testUpdateAbsent() throws DAOException {
        assertFalse(eventDAO.update(getTestEvent()));
    }

    @Test
    void testDeleteAbsent() throws DAOException {
        assertFalse(eventDAO.delete(getTestEvent().getId()));
    }

    @Test
    void testVisitors() throws DAOException {
        int visitors = 100;
        Event testEvent = getTestEvent();
        eventDAO.add(testEvent);
        assertTrue(eventDAO.setVisitors(testEvent.getId(), visitors));

        testEvent = eventDAO.getByTitle(testEvent.getTitle());
        assertEquals(visitors, testEvent.getVisitors());
    }

    @Test
    void testUsersEvents() throws DAOException {
        eventDAO.add(getTestEvent());
        Event testEvent = eventDAO.getByTitle(getTestEvent().getTitle());
        userDAO.add(getTestUser());
        User testUser = userDAO.getByEmail(getTestUser().getEmail());
        userDAO.registerForEvent(testUser.getId(), testEvent.getId());
        List<Event> events = eventDAO.getEventsByUser(testUser.getId());
        assertTrue(events.contains(testEvent));
        assertEquals(1, events.size());
    }

    @Test
    void testUsersEventsWrongMethod() throws DAOException {
        eventDAO.add(getTestEvent());
        Event testEvent = eventDAO.getByTitle(getTestEvent().getTitle());
        userDAO.add(getTestUser());
        User testUser = userDAO.getByEmail(getTestUser().getEmail());
        userDAO.registerForEvent(testUser.getId(), testEvent.getId());
        List<Event> events = eventDAO.getEventsBySpeaker(testUser.getId());
        assertFalse(events.contains(testEvent));
        assertEquals(0, events.size());
    }

    @Test
    void testSpeakersEvents() throws DAOException {
        eventDAO.add(getTestEvent());
        Event testEvent = eventDAO.getByTitle(getTestEvent().getTitle());
        userDAO.add(getTestUser());
        User testUser = userDAO.getByEmail(getTestUser().getEmail());
        Report testReport = getTestReport();
        reportDAO.add(testReport);
        reportDAO.setReportForSpeaker(testUser.getId(), testReport.getId());
        reportDAO.setEventForReport(testEvent.getId(), testReport.getId());
        List<Event> events = eventDAO.getEventsBySpeaker(testUser.getId());
        assertTrue(events.contains(testEvent));
        assertEquals(1, events.size());
    }

    @Test
    void testSpeakersEventsWrongMethod() throws DAOException {
        eventDAO.add(getTestEvent());
        Event testEvent = eventDAO.getByTitle(getTestEvent().getTitle());
        userDAO.add(getTestUser());
        User testUser = userDAO.getByEmail(getTestUser().getEmail());
        Report testReport = getTestReport();
        reportDAO.add(testReport);
        reportDAO.setReportForSpeaker(testUser.getId(), testReport.getId());
        reportDAO.setEventForReport(testEvent.getId(), testReport.getId());
        List<Event> events = eventDAO.getEventsByUser(testUser.getId());
        assertFalse(events.contains(testEvent));
        assertEquals(0, events.size());
    }

    @Test
    void testEventByReport() throws DAOException {
        eventDAO.add(getTestEvent());
        Event testEvent = eventDAO.getByTitle(getTestEvent().getTitle());
        Report testReport = getTestReport();
        reportDAO.add(testReport);
        reportDAO.setEventForReport(testEvent.getId(), testReport.getId());
        Event event = eventDAO.getEventByReport(testReport.getId());
        assertEquals(event, testEvent);
    }

    @Test
    void testEventByReportBadCase() throws DAOException {
        Report testReport = getTestReport();
        reportDAO.add(testReport);
        DAOException exception = assertThrows(DAOException.class, () -> eventDAO.getEventByReport(testReport.getId()));
        assertEquals("No such event", exception.getMessage());
    }
}