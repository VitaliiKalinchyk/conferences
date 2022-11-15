package ua.java.conferences.dao.mysql;

import org.junit.jupiter.api.*;
import ua.java.conferences.entity.*;
import ua.java.conferences.entity.role.Role;
import ua.java.conferences.exception.DBException;

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
    void testCrud() throws DBException {
        Report testReport = getTestReport();
        assertTrue(reportDAO.add(testReport));

        Report resultReport = reportDAO.get(testReport);
        assertNotEquals(0, resultReport.getId());
        assertEquals(resultReport, testReport);
        assertEquals(resultReport.getId(), testReport.getId());
        assertEquals(resultReport.getTopic(), testReport.getTopic());
        assertEquals(resultReport.isAccepted(), testReport.isAccepted());
        assertEquals(resultReport.isApproved(), testReport.isApproved());

        List<Report> reports = reportDAO.getAll();
        assertTrue(reports.contains(resultReport));
        assertEquals(1, reports.size());

        resultReport.setApproved(true);
        assertTrue(reportDAO.update(resultReport));

        Report changedReport = reportDAO.get(resultReport);
        assertTrue(changedReport.isApproved());
        assertEquals(resultReport, changedReport);

        resultReport.setAccepted(true);
        assertTrue(reportDAO.update(resultReport));

        changedReport = reportDAO.get(resultReport);
        assertTrue(changedReport.isAccepted());
        assertEquals(resultReport, changedReport);
        assertTrue(reportDAO.delete(resultReport));

        reports = reportDAO.getAll();
        assertEquals(0, reports.size());
    }

    @Test
    void testGetAbsent() {
        DBException exception = assertThrows(DBException.class, () -> reportDAO.get(getTestReport()));
        assertEquals("No such report", exception.getMessage());
    }

    @Test
    void testUpdateAbsent() throws DBException {
        assertFalse(reportDAO.update(getTestReport()));
    }

    @Test
    void testDeleteAbsent() throws DBException {
        assertFalse(reportDAO.delete(getTestReport()));
    }

    @Test
    void testReportInEvent() throws DBException {
        Report testReport = getTestReport();
        reportDAO.add(testReport);
        Event testEvent = getTestEvent();
        eventDAO.add(testEvent);
        assertTrue(reportDAO.setEventForReport(testEvent, testReport));

        List<Report> reports = reportDAO.getReportsFromEvent(testEvent);
        assertTrue(reports.contains(testReport));
        assertEquals(1, reports.size());
    }

    @Test
    void testWrongEvent() throws DBException {
        Report testReport = getTestReport();
        reportDAO.add(testReport);
        Event testEvent = getTestEvent();
        assertFalse(reportDAO.setEventForReport(testEvent, testReport));
    }

    @Test
    void testWrongReport() throws DBException {
        Report testReport = getTestReport();
        Event testEvent = getTestEvent();
        eventDAO.add(testEvent);
        assertFalse(reportDAO.setEventForReport(testEvent, testReport));
    }

    @Test
    void testWrongReportAndEvent() throws DBException {
        Report testReport = getTestReport();
        Event testEvent = getTestEvent();
        assertFalse(reportDAO.setEventForReport(testEvent, testReport));
    }

    @Test
    void testSpeakerForReport() throws DBException {
        Report testReport = getTestReport();
        reportDAO.add(testReport);
        User testUser = getTestUser();
        userDAO.add(testUser);
        userDAO.setUsersRole(testUser, Role.SPEAKER);
        testUser = userDAO.get(testUser);
        assertTrue(reportDAO.setReportForSpeaker(testUser, testReport));

        List<Report> reports = reportDAO.getReportsFromSpeaker(testUser);
        assertTrue(reports.contains(testReport));
        assertEquals(1, reports.size());
    }

    @Test
    void testWrongReportAndSpeaker() throws DBException {
        Report testReport = getTestReport();
        User testUser = getTestUser();
        assertFalse(reportDAO.setReportForSpeaker(testUser, testReport));
    }
}