package ua.java.conferences.dao.mysql;

import org.junit.jupiter.api.*;
import ua.java.conferences.entities.*;
import ua.java.conferences.exceptions.DAOException;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ua.java.conferences.dao.mysql.DAOTestUtils.*;

class MysqlReportDAOTest {

    @BeforeEach
    void clearDB() throws FileNotFoundException, SQLException {
        createEmptyDB();
    }

    @Test
    void testCrud() throws DAOException {
        eventDAO.add(getTestEvent());
        Report testReport = getTestReport();
        assertDoesNotThrow(() -> reportDAO.add(testReport));

        Report resultReport = reportDAO.getById(1).orElse(null);
        assertNotNull(resultReport);
        assertNotEquals(0, resultReport.getId());
        assertNotEquals(resultReport, testReport);
        assertEquals(resultReport.getTopic(), testReport.getTopic());

        List<Report> reports = reportDAO.getAll();
        assertTrue(reports.contains(resultReport));
        assertEquals(1, reports.size());

        resultReport.setTopic("New Topic");
        assertDoesNotThrow(() -> reportDAO.update(testReport));


        Report changedReport = reportDAO.getById(resultReport.getId()).orElse(null);
        assertNotNull(changedReport);
        assertEquals(resultReport, changedReport);
        assertDoesNotThrow(() -> reportDAO.delete(changedReport.getId()));

        reports = reportDAO.getAll();
        assertEquals(0, reports.size());
    }

    @Test
    void testGetReport() throws DAOException {
        Report testReport = getTestReport();
        Event testEvent = getTestEvent();
        eventDAO.add(testEvent);
        testEvent = eventDAO.getByTitle(testEvent.getTitle()).orElse(testEvent);
        User testSpeaker = getTestUser();
        userDAO.add(testSpeaker);
        testSpeaker = userDAO.getByEmail(testSpeaker.getEmail()).orElse(testSpeaker);
        testReport.setEvent(testEvent);
        testReport.setSpeaker(testSpeaker);
        reportDAO.add(testReport);
        testReport = reportDAO.getById(1).orElse(null);
        assertNotNull(testReport);

        User resultSpeaker = testReport.getSpeaker();
        Event resultEvent = testReport.getEvent();

        assertEquals(resultSpeaker, testSpeaker);
        assertEquals(resultSpeaker.getName(), testSpeaker.getName());
        assertEquals(resultSpeaker.getSurname(), testSpeaker.getSurname());
        assertNotEquals(resultSpeaker.getEmail(), testSpeaker.getEmail());
        assertEquals(resultEvent, testEvent);
        assertEquals(resultEvent.getTitle(), testEvent.getTitle());
        assertEquals(resultEvent.getDate(), testEvent.getDate());
        assertEquals(resultEvent.getLocation(), testEvent.getLocation());
        assertNotEquals(resultEvent.getDescription(), testEvent.getDescription());

    }

    @Test
    void testGetAbsent() throws DAOException {
        assertNull(reportDAO.getById(1).orElse(null));
    }

    @Test
    void testBadInput() {
        assertThrows(DAOException.class, () -> reportDAO.add(getTestReport()));
    }

    @Test
    void testGetEventsReports() throws DAOException {
        Event testEvent = getTestEvent();
        eventDAO.add(testEvent);
        testEvent = eventDAO.getByTitle(testEvent.getTitle()).orElse(null);
        assertNotNull(testEvent);

        Report testReport = getTestReport();
        reportDAO.add(testReport);
        testReport = reportDAO.getById(1L).orElse(null);
        assertNotNull(testReport);

        Event reportEvent = testReport.getEvent();
        assertEquals(testEvent, reportEvent);

        List<Report> reports = reportDAO.getEventsReports(testEvent.getId());
        assertTrue(reports.contains(testReport));
        assertEquals(1, reports.size());
    }

    @Test
    void testWrongReport() throws DAOException {
        Report testReport = getTestReport();
        Event testEvent = getTestEvent();
        eventDAO.add(testEvent);
        List<Report> reports = reportDAO.getEventsReports(testEvent.getId());
        assertFalse(reports.contains(testReport));
        assertEquals(0, reports.size());
    }

    @Test
    void testSpeakerForReport() throws DAOException {
        Event testEvent = getTestEvent();
        eventDAO.add(testEvent);
        Report testReport = getTestReport();
        reportDAO.add(testReport);
        User testUser = getTestUser();
        userDAO.add(testUser);
        assertDoesNotThrow(() -> reportDAO.setSpeaker(1, 1));

        List<Report> reports = reportDAO.getSpeakersReports(1);
        assertEquals(1, reports.size());

        testReport = reportDAO.getById(1).orElse(null);
        assertNotNull(testReport);

        User speaker = testReport.getSpeaker();
        assertEquals(testUser.getName(), speaker.getName());
    }

    @Test
    void testDeleteSpeaker() throws DAOException {
        Event testEvent = getTestEvent();
        eventDAO.add(testEvent);
        Report testReport = getTestReport();
        reportDAO.add(testReport);
        User testUser = getTestUser();
        userDAO.add(testUser);
        reportDAO.setSpeaker(1, 1);

        List<Report> reports = reportDAO.getSpeakersReports(1);
        assertEquals(1, reports.size());

        assertDoesNotThrow(() -> reportDAO.deleteSpeaker(1));

        reports = reportDAO.getSpeakersReports(1);
        assertEquals(0, reports.size());

        testReport = reportDAO.getById(1).orElse(null);
        assertNotNull(testReport);
        assertNull(testReport.getSpeaker());
    }
}