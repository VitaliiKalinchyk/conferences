package ua.java.conferences.services;

import org.junit.jupiter.api.Test;
import ua.java.conferences.dao.ReportDAO;
import ua.java.conferences.dto.request.ReportRequestDTO;
import ua.java.conferences.dto.response.*;
import ua.java.conferences.entities.*;
import ua.java.conferences.exceptions.*;
import ua.java.conferences.services.implementation.ReportServiceImpl;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;
import static ua.java.conferences.Constants.*;
import static ua.java.conferences.exceptions.IncorrectFormatException.*;

class ReportServiceTest {

    private final ReportDAO reportDAO = mock(ReportDAO.class);

    private final ReportService reportService = new ReportServiceImpl(reportDAO);

    @Test
    void testCreateReport() throws DAOException {
        doNothing().when(reportDAO).add(isA(Report.class));
        ReportRequestDTO reportDTO = getTestReportRequestDTO();
        assertDoesNotThrow(() -> reportService.createReport(reportDTO));
    }

    @Test
    void testCreateIncorrectReport() throws DAOException {
        doNothing().when(reportDAO).add(isA(Report.class));
        ReportRequestDTO reportDTO = new ReportRequestDTO(ID, WRONG_TOPIC, ID, ID);
        IncorrectFormatException e = assertThrows(IncorrectFormatException.class , () -> reportService.createReport(reportDTO));
        assertEquals(Message.TOPIC, e.getMessage());
    }

    @Test
    void testViewReport() throws DAOException, ServiceException {
        when(reportDAO.getById(ID)).thenReturn(Optional.of(getTestReport()));
        assertEquals(getTestReportResponseDTO(), reportService.view(ID));
    }

    @Test
    void testViewNoReport() throws DAOException {
        when(reportDAO.getById(ID)).thenReturn(Optional.empty());
        assertThrows(NoSuchReportException.class,() -> reportService.view(ID));
    }

    @Test
    void testViewEventsReports() throws DAOException, ServiceException {
        List<Report> reports = new ArrayList<>();
        List<ReportResponseDTO> reportDTOs = new ArrayList<>();
        reports.add(getTestReport());
        reportDTOs.add(getTestReportResponseDTO());
        when(reportDAO.getEventsReports(ID)).thenReturn(reports);
        assertIterableEquals(reportDTOs, reportService.viewEventsReports(ID));
    }

    @Test
    void testViewSpeakersReports() throws DAOException, ServiceException {
        List<Report> reports = new ArrayList<>();
        List<SpeakersReportResponseDTO> reportDTOs = new ArrayList<>();
        reports.add(getTestReport());
        reportDTOs.add(getTestSpeakerReportResponseDTO());
        when(reportDAO.getSpeakersReports(ID)).thenReturn(reports);
        assertIterableEquals(reportDTOs, reportService.viewSpeakersReports(ID));
    }

    @Test
    void testUpdateReport() throws DAOException {
        doNothing().when(reportDAO).update(isA(Report.class));
        assertDoesNotThrow(() -> reportService.updateReport(getTestReportRequestDTO()));
    }

    @Test
    void testSetSpeaker() throws DAOException {
        doNothing().when(reportDAO).setSpeaker(isA(long.class), isA(long.class));
        assertDoesNotThrow(() -> reportService.setSpeaker(ID, ID));
    }

    @Test
    void testDeleteSpeaker() throws DAOException {
        doNothing().when(reportDAO).deleteSpeaker(isA(long.class));
        assertDoesNotThrow(() -> reportService.deleteSpeaker(ID));
    }

    @Test
    void testDeleteReport() throws DAOException {
        doNothing().when(reportDAO).delete(isA(long.class));
        assertDoesNotThrow(() -> reportService.delete(ID));
    }

    private ReportRequestDTO getTestReportRequestDTO() {
        return new ReportRequestDTO(ID, TOPIC, ID, ID);
    }

    private ReportResponseDTO getTestReportResponseDTO() {
        return new ReportResponseDTO(ID, TOPIC, ID, SPEAKER_NAME);
    }

    private SpeakersReportResponseDTO getTestSpeakerReportResponseDTO() {
        return new SpeakersReportResponseDTO(ID, TOPIC, ID, TITLE, DATE_NAME, LOCATION);
    }

    private Report getTestReport() {
        User speaker = new User.UserBuilder()
                .setId(ID)
                .setEmail(EMAIL)
                .setPassword(PASSWORD)
                .setName(NAME)
                .setSurname(SURNAME)
                .get();
        Event event = new Event.EventBuilder()
                .setId(ID)
                .setTitle(TITLE)
                .setDate(DATE)
                .setLocation(LOCATION)
                .setDescription(DESCRIPTION)
                .get();
        return new Report.ReportBuilder()
                .setId(ID)
                .setTopic(TOPIC)
                .setEvent(event)
                .setSpeaker(speaker)
                .get();
    }
}