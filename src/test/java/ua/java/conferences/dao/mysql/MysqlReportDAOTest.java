package ua.java.conferences.dao.mysql;

import org.junit.jupiter.api.*;
import ua.java.conferences.entity.*;
import ua.java.conferences.entity.role.Role;
import ua.java.conferences.exception.DAOException;

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
        Report testReport = getTestReport();
        assertTrue(reportDAO.add(testReport));

        Report resultReport = reportDAO.getById(testReport.getId());
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

        Report changedReport = reportDAO.getById(resultReport.getId());
        assertTrue(changedReport.isApproved());
        assertEquals(resultReport, changedReport);

        resultReport.setAccepted(true);
        assertTrue(reportDAO.update(resultReport));

        changedReport = reportDAO.getById(resultReport.getId());
        assertTrue(changedReport.isAccepted());
        assertEquals(resultReport, changedReport);
        assertTrue(reportDAO.delete(resultReport.getId()));

        reports = reportDAO.getAll();
        assertEquals(0, reports.size());
    }

    @Test
    void testGetAbsent() {
        DAOException exception = assertThrows(DAOException.class, () -> reportDAO.getById(getTestReport().getId()));
        assertEquals("No such report", exception.getMessage());
    }

    @Test
    void testUpdateAbsent() throws DAOException {
        assertFalse(reportDAO.update(getTestReport()));
    }

    @Test
    void testDeleteAbsent() throws DAOException {
        assertFalse(reportDAO.delete(getTestReport().getId()));
    }

    @Test
    void testReportInEvent() throws DAOException {
        Report testReport = getTestReport();
        reportDAO.add(testReport);
        Event testEvent = getTestEvent();
        eventDAO.add(testEvent);
        assertTrue(reportDAO.setEventForReport(testEvent.getId(), testReport.getId()));

        List<Report> reports = reportDAO.getReportsFromEvent(testEvent.getId());
        assertTrue(reports.contains(testReport));
        assertEquals(1, reports.size());
    }

    @Test
    void testWrongEvent() throws DAOException {
        Report testReport = getTestReport();
        reportDAO.add(testReport);
        Event testEvent = getTestEvent();
        assertFalse(reportDAO.setEventForReport(testEvent.getId(), testReport.getId()));
    }

    @Test
    void testWrongReport() throws DAOException {
        Report testReport = getTestReport();
        Event testEvent = getTestEvent();
        eventDAO.add(testEvent);
        assertFalse(reportDAO.setEventForReport(testEvent.getId(), testReport.getId()));
    }

    @Test
    void testWrongReportAndEvent() throws DAOException {
        Report testReport = getTestReport();
        Event testEvent = getTestEvent();
        assertFalse(reportDAO.setEventForReport(testEvent.getId(), testReport.getId()));
    }

    @Test
    void testSpeakerForReport() throws DAOException {
        Report testReport = getTestReport();
        reportDAO.add(testReport);
        User testUser = getTestUser();
        userDAO.add(testUser);
        userDAO.setUsersRole(testUser.getId(), Role.SPEAKER);
        testUser = userDAO.getByEmail(testUser.getEmail());
        assertTrue(reportDAO.setReportForSpeaker(testUser.getId(), testReport.getId()));

        List<Report> reports = reportDAO.getReportsFromSpeaker(testUser.getId());
        assertTrue(reports.contains(testReport));
        assertEquals(1, reports.size());
    }

    @Test
    void testWrongReportAndSpeaker() throws DAOException {
        Report testReport = getTestReport();
        User testUser = getTestUser();
        assertFalse(reportDAO.setReportForSpeaker(testUser.getId(), testReport.getId()));
    }
}