package ua.java.conferences.utils;

import org.junit.jupiter.api.Test;
import ua.java.conferences.dto.request.*;
import ua.java.conferences.dto.response.*;
import ua.java.conferences.entities.*;

import static org.junit.jupiter.api.Assertions.*;
import static ua.java.conferences.Constants.*;
import static ua.java.conferences.utils.ConvertorUtil.*;

class ConvertorUtilTest {

    @Test
    void testConvertDTOToUser() {
        User testUser = getTestUser();
        User dtoToUser = convertDTOToUser(getTestUserRequestDTO());
        assertEquals(testUser, dtoToUser);
        assertEquals(testUser.getEmail(), dtoToUser.getEmail());
        assertNotEquals(testUser.getPassword(), dtoToUser.getPassword());
        assertEquals(testUser.getName(), dtoToUser.getName());
        assertEquals(testUser.getSurname(), dtoToUser.getSurname());
        assertEquals(testUser.isEmailNotification(), dtoToUser.isEmailNotification());
    }

    @Test
    void testConvertUserToDTO() {
        UserResponseDTO testDTO = getTestUserResponseDTO();
        UserResponseDTO userToDTO = convertUserToDTO(getTestUser());
        assertEquals(testDTO, userToDTO);
    }

    @Test
    void testConvertDTOToReport() {
        Report testReport = getTestReport();
        Report dtoToReport = convertDTOToReport(getTestReportRequestDTO());
        assertEquals(testReport, dtoToReport);
        assertEquals(testReport.getTopic(), dtoToReport.getTopic());
        assertEquals(testReport.getEvent(), dtoToReport.getEvent());
        assertEquals(testReport.getSpeaker(), dtoToReport.getSpeaker());
    }

    @Test
    void testConvertReportToDTO() {
        ReportResponseDTO testDTO = getTestReportResponseDTO();
        ReportResponseDTO reportToDTO = convertReportToDTO(getTestReport());
        assertEquals(testDTO, reportToDTO);
    }

    @Test
    void testConvertSpeakersReportToDTO() {
        ReportResponseDTO testDTO = getTestSpeakerReportResponseDTO();
        ReportResponseDTO reportToDTO = convertSpeakersReportToDTO(getTestReport());
        assertEquals(testDTO, reportToDTO);
    }

    @Test
    void testConvertDTOToEvent() {
        Event testEvent = getTestEvent();
        Event dtoToEvent = convertDTOToEvent(getTestEventRequestDTO());
        assertEquals(testEvent, dtoToEvent);
        assertEquals(testEvent.getTitle(), dtoToEvent.getTitle());
        assertEquals(testEvent.getDate(), dtoToEvent.getDate());
        assertEquals(testEvent.getLocation(), dtoToEvent.getLocation());
        assertEquals(testEvent.getDescription(), dtoToEvent.getDescription());
    }

    @Test
    void  testConvertEventToDTO() {
        EventResponseDTO testDTO = getTestEventResponseDTO();
        EventResponseDTO eventToDTO = convertEventToDTO(getTestEvent());
        assertEquals(testDTO, eventToDTO);
    }

    @Test
    void testConvertEventToFullDTO() {
        EventResponseDTO testDTO = getTestFullEventResponseDTO();
        EventResponseDTO eventToDTO = convertEventToFullDTO(getTestEvent());
        assertEquals(testDTO, eventToDTO);
    }

    private UserRequestDTO getTestUserRequestDTO() {
        return new UserRequestDTO(ID, EMAIL, PASSWORD, NAME, SURNAME, NOTIFICATION);
    }

    private ReportRequestDTO getTestReportRequestDTO() {
        return new ReportRequestDTO(ID, TOPIC, ID, ID);
    }

    private EventRequestDTO getTestEventRequestDTO() {
        return new EventRequestDTO(ID, TITLE, DATE_NAME, LOCATION, DESCRIPTION);
    }

    private UserResponseDTO getTestUserResponseDTO() {
        return new UserResponseDTO(ID, EMAIL, NAME, SURNAME, NOTIFICATION, ROLE_NAME);
    }

    private ReportResponseDTO getTestReportResponseDTO() {
        return new ReportResponseDTO(ID, TOPIC, ID, SPEAKER_NAME);
    }

    private ReportResponseDTO getTestSpeakerReportResponseDTO() {
        return new ReportResponseDTO(ID, TOPIC, ID, TITLE, DATE_NAME, LOCATION);
    }

    private EventResponseDTO getTestEventResponseDTO() {
        return new EventResponseDTO(ID, TITLE, DATE_NAME, LOCATION, DESCRIPTION);
    }

    private EventResponseDTO getTestFullEventResponseDTO() {
        return new EventResponseDTO(ID, TITLE, DATE_NAME, LOCATION, REPORTS, REGISTRATIONS, VISITORS);
    }

    private User getTestUser() {
        return new User.UserBuilder()
                .setId(ID)
                .setEmail(EMAIL)
                .setPassword(PASSWORD)
                .setName(NAME)
                .setSurname(SURNAME)
                .setRoleId(ROLE_ID)
                .get();
    }

    private Event getTestEvent() {
        return new Event.EventBuilder()
                .setId(ID)
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
        return new Report.ReportBuilder()
                .setId(ID)
                .setTopic(TOPIC)
                .setSpeaker(getTestUser())
                .setEvent(getTestEvent())
                .get();
    }
}