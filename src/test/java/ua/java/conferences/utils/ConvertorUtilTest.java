package ua.java.conferences.utils;

import org.junit.jupiter.api.Test;
import ua.java.conferences.dto.*;
import ua.java.conferences.entities.*;

import static org.junit.jupiter.api.Assertions.*;
import static ua.java.conferences.Constants.*;
import static ua.java.conferences.utils.ConvertorUtil.*;

class ConvertorUtilTest {

    @Test
    void testConvertDTOToUser() {
        User testUser = getTestUser();
        User dtoToUser = convertDTOToUser(getTestUserDTO());
        assertEquals(testUser, dtoToUser);
        assertEquals(testUser.getId(), dtoToUser.getId());
        assertEquals(testUser.isEmailNotification(), dtoToUser.isEmailNotification());
    }

    @Test
    void testConvertUserToDTO() {
        UserDTO testDTO = getTestUserDTO();
        UserDTO userToDTO = convertUserToDTO(getTestUser());
        assertEquals(testDTO, userToDTO);
        assertEquals(testDTO.getId(), userToDTO.getId());
        assertNotEquals(testDTO.getPassword(), userToDTO.getPassword());
        assertEquals(testDTO.isNotification(), userToDTO.isNotification());
    }

    @Test
    void testConvertDTOToReport() {
        Report testReport = getTestReport();
        Report dtoToReport = convertDTOToReport(getTestReportDTO());
        assertEquals(testReport, dtoToReport);
        assertEquals(testReport.getTopic(), dtoToReport.getTopic());
        assertEquals(testReport.getEvent().getId(), dtoToReport.getEvent().getId());
        assertEquals(testReport.getSpeaker().getId(), dtoToReport.getSpeaker().getId());
    }

    @Test
    void testConvertReportToDTO() {
        ReportDTO testDTO = getTestReportDTO();
        ReportDTO reportToDTO = convertReportToDTO(getTestReport());
        assertEquals(testDTO, reportToDTO);
    }

    @Test
    void testConvertSpeakersReportToDTO() {
        ReportDTO testDTO = getTestReportDTO();
        ReportDTO reportToDTO = convertReportToDTO(getTestReport());
        assertEquals(testDTO, reportToDTO);
    }

    @Test
    void testConvertDTOToEvent() {
        Event testEvent = getTestEvent();
        Event dtoToEvent = convertDTOToEvent(getTestEventDTO());
        assertEquals(testEvent, dtoToEvent);
        assertEquals(testEvent.getTitle(), dtoToEvent.getTitle());
        assertEquals(testEvent.getDate(), dtoToEvent.getDate());
        assertEquals(testEvent.getLocation(), dtoToEvent.getLocation());
        assertEquals(testEvent.getDescription(), dtoToEvent.getDescription());
    }

    @Test
    void  testConvertEventToDTO() {
        EventDTO testDTO = getTestEventDTO();
        EventDTO eventToDTO = convertEventToDTO(getTestEvent());
        assertEquals(testDTO, eventToDTO);
    }

    @Test
    void testConvertEventToFullDTO() {
        EventDTO testDTO = getTestFullEventDTO();
        EventDTO eventToDTO = convertEventToDTO(getTestEvent());
        assertEquals(testDTO, eventToDTO);
    }

    private UserDTO getTestUserDTO() {
        return new UserDTO.Builder()
                .setId(ID_VALUE)
                .setEmail(EMAIL)
                .setPassword(PASSWORD)
                .setName(NAME)
                .setSurname(SURNAME)
                .setNotification(NOTIFICATION)
                .setRole(ROLE_VISITOR)
                .get();
    }

    private ReportDTO getTestReportDTO() {
        return new ReportDTO.Builder()
                .setId(ID_VALUE)
                .setTopic(TOPIC)
                .setSpeakerId(ID_VALUE)
                .setSpeakerName(SPEAKER_NAME)
                .setEventId(ID_VALUE)
                .setTitle(TITLE)
                .setDate(DATE_NAME)
                .setLocation(LOCATION)
                .get();
    }

    private EventDTO getTestEventDTO() {
        return new EventDTO.Builder()
                .setId(ID_VALUE)
                .setTitle(TITLE)
                .setDate(DATE_NAME)
                .setLocation(LOCATION)
                .setDescription(DESCRIPTION)
                .get();
    }

    private EventDTO getTestFullEventDTO() {
        return new EventDTO.Builder()
                .setId(ID_VALUE)
                .setTitle(TITLE)
                .setDate(DATE_NAME)
                .setLocation(LOCATION)
                .setReports(REPORTS)
                .setRegistrations(REGISTRATIONS)
                .setVisitors(VISITORS)
                .get();
    }

    private User getTestUser() {
        return new User.Builder()
                .setId(ID_VALUE)
                .setEmail(EMAIL)
                .setPassword(PASSWORD)
                .setName(NAME)
                .setSurname(SURNAME)
                .setRoleId(ROLE_ID)
                .get();
    }

    private Event getTestEvent() {
        return new Event.Builder()
                .setId(ID_VALUE)
                .setTitle(TITLE)
                .setDate(DATE)
                .setLocation(LOCATION)
                .setDescription(DESCRIPTION)
                .setRegistrations(REGISTRATIONS)
                .setVisitors(VISITORS)
                .setReports(REPORTS)
                .get();
    }

    private Report getTestReport() {
        return new Report.Builder()
                .setId(ID_VALUE)
                .setTopic(TOPIC)
                .setSpeaker(getTestUser())
                .setEvent(getTestEvent())
                .get();
    }
}