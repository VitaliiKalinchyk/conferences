package ua.java.conferences.services;

import org.junit.jupiter.api.Test;
import ua.java.conferences.dao.ReportDAO;
import ua.java.conferences.dto.ReportDTO;
import ua.java.conferences.entities.*;
import ua.java.conferences.exceptions.*;
import ua.java.conferences.services.implementation.ReportServiceImpl;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;
import static ua.java.conferences.Constants.*;
import static ua.java.conferences.exceptions.constants.Message.*;

class ReportServiceTest {

    private final ReportDAO reportDAO = mock(ReportDAO.class);

    private final ReportService reportService = new ReportServiceImpl(reportDAO);

    @Test
    void testCreateReport() throws DAOException {
        doNothing().when(reportDAO).add(isA(Report.class));
        ReportDTO reportDTO = getReportDTO();
        assertDoesNotThrow(() -> reportService.addReport(reportDTO));
    }

    @Test
    void testCreateIncorrectTopic() throws DAOException {
        doNothing().when(reportDAO).add(isA(Report.class));
        ReportDTO reportDTO = getReportDTO();
        reportDTO.setTopic(INCORRECT_TOPIC);
        IncorrectFormatException e = assertThrows(IncorrectFormatException.class , () -> reportService.addReport(reportDTO));
        assertEquals(ENTER_CORRECT_TOPIC, e.getMessage());
    }

    @Test
    void testViewReport() throws DAOException, ServiceException {
        when(reportDAO.getById(ID_VALUE)).thenReturn(Optional.of(getTestReport()));
        assertEquals(getReportDTO(), reportService.getById(String.valueOf(ID_VALUE)));
    }

    @Test
    void testViewNoReport() throws DAOException {
        when(reportDAO.getById(ID_VALUE)).thenReturn(Optional.empty());
        assertThrows(NoSuchReportException.class,() -> reportService.getById(String.valueOf(ID_VALUE)));
    }

    @Test
    void testGetAll() throws DAOException, ServiceException {
        List<Report> reports = new ArrayList<>();
        List<ReportDTO> reportDTOs = new ArrayList<>();
        reports.add(getTestReport());
        reportDTOs.add(getReportDTO());
        when(reportDAO.getAll()).thenReturn(reports);
        assertIterableEquals(reportDTOs, reportService.getAll());
    }

    @Test
    void testViewEventsReports() throws DAOException, ServiceException {
        List<Report> reports = new ArrayList<>();
        List<ReportDTO> reportDTOs = new ArrayList<>();
        reports.add(getTestReport());
        reportDTOs.add(getReportDTO());
        when(reportDAO.getEventsReports(ID_VALUE)).thenReturn(reports);
        assertIterableEquals(reportDTOs, reportService.viewEventsReports(String.valueOf(ID_VALUE)));
    }

    @Test
    void testViewSpeakersReports() throws DAOException, ServiceException {
        List<Report> reports = new ArrayList<>();
        List<ReportDTO> reportDTOs = new ArrayList<>();
        reports.add(getTestReport());
        reportDTOs.add(getReportDTO());
        when(reportDAO.getSpeakersReports(ID_VALUE)).thenReturn(reports);
        assertIterableEquals(reportDTOs, reportService.viewSpeakersReports(ID_VALUE));
    }

    @Test
    void testUpdateReport() throws DAOException {
        doNothing().when(reportDAO).update(isA(Report.class));
        assertDoesNotThrow(() -> reportService.update(getReportDTO()));
    }

    @Test
    void testUpdateReportIncorrectTopic() throws DAOException {
        doNothing().when(reportDAO).update(isA(Report.class));
        ReportDTO reportDTO = getReportDTO();
        reportDTO.setTopic(INCORRECT_TOPIC);
        IncorrectFormatException e = assertThrows(IncorrectFormatException.class , () -> reportService.update(reportDTO));
        assertEquals(ENTER_CORRECT_TOPIC, e.getMessage());
    }

    @Test
    void testSetSpeaker() throws DAOException {
        doNothing().when(reportDAO).setSpeaker(isA(long.class), isA(long.class));
        assertDoesNotThrow(() -> reportService.setSpeaker(ID_VALUE, ID_VALUE));
    }

    @Test
    void testDeleteSpeaker() throws DAOException {
        doNothing().when(reportDAO).deleteSpeaker(isA(long.class));
        assertDoesNotThrow(() -> reportService.deleteSpeaker(ID_VALUE));
    }

    @Test
    void testDeleteReport() throws DAOException {
        doNothing().when(reportDAO).delete(isA(long.class));
        assertDoesNotThrow(() -> reportService.delete(String.valueOf(ID_VALUE)));
    }

    private ReportDTO getReportDTO() {
        return ReportDTO.builder()
                .id(ID_VALUE)
                .topic(TOPIC)
                .eventId(ID_VALUE)
                .title(TITLE)
                .date(DATE_NAME)
                .location(LOCATION)
                .build();
    }

    private Report getTestReport() {
        User speaker = User.builder()
                .id(ID_VALUE)
                .email(EMAIL)
                .password(PASSWORD)
                .name(NAME)
                .surname(SURNAME)
                .build();
        Event event = Event.builder()
                .id(ID_VALUE)
                .title(TITLE)
                .date(DATE)
                .location(LOCATION)
                .description(DESCRIPTION)
                .build();
        return Report.builder()
                .id(ID_VALUE)
                .topic(TOPIC)
                .event(event)
                .speaker(speaker)
                .build();
    }
}