package ua.java.conferences.model.dao;

import org.junit.jupiter.api.*;
import ua.java.conferences.exceptions.DAOException;
import ua.java.conferences.model.entities.Event;
import ua.java.conferences.model.entities.Report;
import ua.java.conferences.model.entities.User;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ua.java.conferences.model.dao.DAOTestUtils.*;
import static ua.java.conferences.Constants.*;

class ReportDAOTest {

    @BeforeEach
    void clearDB() throws FileNotFoundException, SQLException {
        createEmptyDB();
    }

    @Test
    void testAdd() throws DAOException {
        eventDAO.add(getTestEvent());
        userDAO.add(getTestUser());
        assertDoesNotThrow(() -> reportDAO.add(getTestReport()));
    }

    @Test
    void testAddNoSpeaker() throws DAOException {
        eventDAO.add(getTestEvent());
        Report testReport = getTestReport();
        testReport.setSpeaker(null);
        assertDoesNotThrow(() -> reportDAO.add(testReport));
    }

    @Test
    void testAddNoEvent() {
        assertThrows(DAOException.class, () -> reportDAO.add(getTestReport()));
    }

    @Test
    void testAddNoSuchSpeaker() throws DAOException {
        eventDAO.add(getTestEvent());
        assertThrows(DAOException.class, () -> reportDAO.add(getTestReport()));
    }

    @Test
    void testGetById() throws DAOException {
        prepareDb();

        Report resultReport = reportDAO.getById(ID_VALUE).orElse(null);
        assertNotNull(resultReport);
        assertEquals(getTestReport().getId(), resultReport.getId());
    }

    @Test
    void testGetAbsent() throws DAOException {
        assertNull(reportDAO.getById(ID_VALUE).orElse(null));
    }

    @Test
    void testGetReport() throws DAOException {
        prepareDb();

        Report resultReport = reportDAO.getById(ID_VALUE).orElse(null);
        assertNotNull(resultReport);

        User resultSpeaker = resultReport.getSpeaker();
        Event resultEvent = resultReport.getEvent();

        assertEquals(resultSpeaker.getId(), getTestUser().getId());
        assertEquals(resultSpeaker.getName(), getTestUser().getName());
        assertEquals(resultSpeaker.getSurname(), getTestUser().getSurname());
        assertEquals(resultEvent.getId(), getTestEvent().getId());
        assertEquals(resultEvent.getTitle(), getTestEvent().getTitle());
        assertEquals(resultEvent.getDate(), getTestEvent().getDate());
        assertEquals(resultEvent.getLocation(), getTestEvent().getLocation());

    }

    @Test
    void testGetAll() throws DAOException {
        prepareDb();

        List<Report> reports = reportDAO.getAll();
        assertEquals(ONE, reports.size());
    }

    @Test
    void testGetNoReports() throws DAOException {
        List<Report> reports = reportDAO.getAll();
        assertEquals(ZERO, reports.size());
    }

    @Test
    void testUpdate() throws DAOException {
        prepareDb();

        assertDoesNotThrow(() -> reportDAO.update(getTestReport()));
    }

    @Test
    void testUpdateWithResult() throws DAOException {
        prepareDb();

        Report testReport = getTestReport();
        testReport.setTopic("Result");
        reportDAO.update(testReport);

        Report resultReport = reportDAO.getById(ID_VALUE).orElse(null);
        assertNotNull(resultReport);
        assertEquals(resultReport.getTopic(), testReport.getTopic());
    }


    @Test
    void testUpdateNoReport() {
        assertDoesNotThrow(() -> reportDAO.update(getTestReport()));
    }

    @Test
    void testDelete() throws DAOException {
        prepareDb();

        assertDoesNotThrow(() -> reportDAO.delete(ID_VALUE));
        List<Report> reports = reportDAO.getAll();
        assertEquals(ZERO, reports.size());
    }

    @Test
    void testDeleteNoReport() {
        assertDoesNotThrow(() -> reportDAO.delete(ID_VALUE));
    }

    @Test
    void testGetEventsReports() throws DAOException {
        prepareDb();

        List<Report> reports = reportDAO.getEventsReports(ID_VALUE);
        assertEquals(ONE, reports.size());
    }

    @Test
    void testNoReport() throws DAOException {
        eventDAO.add( getTestEvent());
        List<Report> reports = reportDAO.getEventsReports(ID_VALUE);
        assertEquals(ZERO, reports.size());
    }

    @Test
    void testGetSpeakersReports() throws DAOException {
        prepareDb();

        List<Report> reports = reportDAO.getSpeakersReports(ID_VALUE);
        assertEquals(ONE, reports.size());
    }

    @Test
    void testSetSpeakerForReport() throws DAOException {
        eventDAO.add(getTestEvent());
        Report testReport = getTestReport();
        testReport.setSpeaker(null);
        reportDAO.add(testReport);
        userDAO.add(getTestUser());
        assertDoesNotThrow(() -> reportDAO.setSpeaker(ID_VALUE, ID_VALUE));

        List<Report> reports = reportDAO.getSpeakersReports(ID_VALUE);
        assertEquals(ONE, reports.size());

        testReport = reportDAO.getById(ID_VALUE).orElse(null);
        assertNotNull(testReport);

        User speaker = testReport.getSpeaker();
        assertEquals(getTestUser().getName(), speaker.getName());
    }

    @Test
    void testDeleteSpeaker() throws DAOException {
        prepareDb();

        List<Report> reports = reportDAO.getSpeakersReports(ID_VALUE);
        assertEquals(ONE, reports.size());

        assertDoesNotThrow(() -> reportDAO.deleteSpeaker(ID_VALUE));

        reports = reportDAO.getSpeakersReports(ID_VALUE);
        assertEquals(ZERO, reports.size());

        Report testReport = reportDAO.getById(ID_VALUE).orElse(null);
        assertNotNull(testReport);
        assertNull(testReport.getSpeaker());
    }

    private static void prepareDb() throws DAOException {
        eventDAO.add(getTestEvent());
        userDAO.add(getTestUser());
        reportDAO.add(getTestReport());
    }
}