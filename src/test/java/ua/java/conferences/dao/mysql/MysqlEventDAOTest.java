package ua.java.conferences.dao.mysql;

import org.junit.jupiter.api.*;
import ua.java.conferences.entity.*;
import ua.java.conferences.exception.DBException;

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
    void testCrud() throws DBException {
        Event testEvent = getTestEvent();
        assertTrue(eventDAO.add(testEvent));
        Event resultEvent = eventDAO.get(testEvent);
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
        Event changedEvent = eventDAO.get(resultEvent);
        assertEquals("Result", changedEvent.getTitle());
        assertEquals(resultEvent, changedEvent);
        assertTrue(eventDAO.delete(resultEvent));

        events = eventDAO.getAll();
        assertEquals(0, events.size());
    }

    @Test
    void testAddTwice() throws DBException {
        assertTrue(eventDAO.add(getTestEvent()));
        assertFalse(eventDAO.add(getTestEvent()));
    }

    @Test
    void testGetAbsent() {
        DBException exception = assertThrows(DBException.class, () -> eventDAO.get(getTestEvent()));
        assertEquals("No such event", exception.getMessage());
    }

    @Test
    void testUpdateAbsent() throws DBException {
        assertFalse(eventDAO.update(getTestEvent()));
    }

    @Test
    void testDeleteAbsent() throws DBException {
        assertFalse(eventDAO.delete(getTestEvent()));
    }

    @Test
    void testVisitors() throws DBException {
        int visitors = 100;
        Event testEvent = getTestEvent();
        eventDAO.add(testEvent);
        assertTrue(eventDAO.setVisitors(testEvent, visitors));

        testEvent = eventDAO.get(testEvent);
        assertEquals(visitors, testEvent.getVisitors());
    }

    @Test
    void testUsersEvents() throws DBException {
        eventDAO.add(getTestEvent());
        Event testEvent = eventDAO.get(getTestEvent());
        userDAO.add(getTestUser());
        User testUser = userDAO.get(getTestUser());
        userDAO.registerForEvent(testUser, testEvent);
        List<Event> events = eventDAO.getEventsByUser(testUser);
        assertTrue(events.contains(testEvent));
        assertEquals(1, events.size());
    }

    @Test
    void testUsersEventsWrongMethod() throws DBException {
        eventDAO.add(getTestEvent());
        Event testEvent = eventDAO.get(getTestEvent());
        userDAO.add(getTestUser());
        User testUser = userDAO.get(getTestUser());
        userDAO.registerForEvent(testUser, testEvent);
        List<Event> events = eventDAO.getEventsBySpeaker(testUser);
        assertFalse(events.contains(testEvent));
        assertEquals(0, events.size());
    }

    @Test
    void testSpeakersEvents() throws DBException {
        eventDAO.add(getTestEvent());
        Event testEvent = eventDAO.get(getTestEvent());
        userDAO.add(getTestUser());
        User testUser = userDAO.get(getTestUser());
        reportDAO.add(getTestReport());
        Report testReport = reportDAO.get(getTestReport());
        reportDAO.setReportForSpeaker(testUser, testReport);
        reportDAO.setEventForReport(testEvent, testReport);
        List<Event> events = eventDAO.getEventsBySpeaker(testUser);
        assertTrue(events.contains(testEvent));
        assertEquals(1, events.size());
    }

    @Test
    void testSpeakersEventsWrongMethod() throws DBException {
        eventDAO.add(getTestEvent());
        Event testEvent = eventDAO.get(getTestEvent());
        userDAO.add(getTestUser());
        User testUser = userDAO.get(getTestUser());
        reportDAO.add(getTestReport());
        Report testReport = reportDAO.get(getTestReport());
        reportDAO.setReportForSpeaker(testUser, testReport);
        reportDAO.setEventForReport(testEvent, testReport);
        List<Event> events = eventDAO.getEventsByUser(testUser);
        assertFalse(events.contains(testEvent));
        assertEquals(0, events.size());
    }

    @Test
    void testEventByReport() throws DBException {
        eventDAO.add(getTestEvent());
        Event testEvent = eventDAO.get(getTestEvent());
        reportDAO.add(getTestReport());
        Report testReport = reportDAO.get(getTestReport());
        reportDAO.setEventForReport(testEvent, testReport);
        Event event = eventDAO.getEventByReport(testReport);
        assertEquals(event, testEvent);
    }

    @Test
    void testEventByReportBadCase() throws DBException {
        reportDAO.add(getTestReport());
        Report testReport = reportDAO.get(getTestReport());
        DBException exception = assertThrows(DBException.class, () -> eventDAO.getEventByReport(testReport));
        assertEquals("No such event", exception.getMessage());
    }
}