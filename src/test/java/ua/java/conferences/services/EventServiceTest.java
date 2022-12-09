package ua.java.conferences.services;

import org.junit.jupiter.api.Test;
import ua.java.conferences.dao.EventDAO;
import ua.java.conferences.dto.request.EventRequestDTO;
import ua.java.conferences.dto.response.*;
import ua.java.conferences.entities.Event;

import ua.java.conferences.exceptions.*;
import ua.java.conferences.services.implementation.EventServiceImpl;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static ua.java.conferences.Constants.*;
import static ua.java.conferences.exceptions.IncorrectFormatException.Message.*;
import static ua.java.conferences.dao.mysql.constants.EventSQLQueries.*;

class EventServiceTest {

    private final EventDAO eventDAO = mock(EventDAO.class);

    private final EventService eventService = new EventServiceImpl(eventDAO);

    @Test
    void testCreateEvent() throws DAOException {
        doNothing().when(eventDAO).add(isA(Event.class));
        EventRequestDTO eventDTO = getTestEventRequestDTO();
        assertDoesNotThrow(() -> eventService.createEvent(eventDTO));
    }

    @Test
    void testCreateIncorrectTitle() throws DAOException {
        doNothing().when(eventDAO).add(isA(Event.class));
        EventRequestDTO eventDTO = new EventRequestDTO(ID, WRONG_TITLE,DATE_NAME, LOCATION, DESCRIPTION);
        IncorrectFormatException e = assertThrows(IncorrectFormatException.class , () -> eventService.createEvent(eventDTO));
        assertEquals(ENTER_CORRECT_TITLE, e.getMessage());
    }

    @Test
    void testCreateIncorrectDate() throws DAOException {
        doNothing().when(eventDAO).add(isA(Event.class));
        EventRequestDTO eventDTO = new EventRequestDTO(ID, TITLE,WRONG_DATE_NAME, LOCATION, DESCRIPTION);
        IncorrectFormatException e = assertThrows(IncorrectFormatException.class , () -> eventService.createEvent(eventDTO));
        assertEquals(ENTER_VALID_DATE, e.getMessage());
    }

    @Test
    void testCreateIncorrectLocation() throws DAOException {
        doNothing().when(eventDAO).add(isA(Event.class));
        EventRequestDTO eventDTO = new EventRequestDTO(ID, TITLE,DATE_NAME, WRONG_LOCATION, DESCRIPTION);
        IncorrectFormatException e = assertThrows(IncorrectFormatException.class , () -> eventService.createEvent(eventDTO));
        assertEquals(ENTER_CORRECT_LOCATION, e.getMessage());
    }

    @Test
    void testCreateIncorrectDescription() throws DAOException {
        doNothing().when(eventDAO).add(isA(Event.class));
        EventRequestDTO eventDTO = new EventRequestDTO(ID, TITLE,DATE_NAME, LOCATION, WRONG_DESCRIPTION);
        IncorrectFormatException e = assertThrows(IncorrectFormatException.class , () -> eventService.createEvent(eventDTO));
        assertEquals(ENTER_CORRECT_DESCRIPTION, e.getMessage());
    }

    @Test
    void testDuplicateTitle() throws DAOException {
        doThrow(new DAOException(new SQLException("Duplicate entry"))).when(eventDAO).add(isA(Event.class));
        EventRequestDTO eventDTO = new EventRequestDTO(ID, TITLE,DATE_NAME, LOCATION, DESCRIPTION);
        ServiceException e = assertThrows(ServiceException.class , () -> eventService.createEvent(eventDTO));
        assertTrue(e.getMessage().contains("Duplicate entry"));
    }

    @Test
    void testDbIsDown() throws DAOException {
        doThrow(new DAOException(new SQLException())).when(eventDAO).add(isA(Event.class));
        EventRequestDTO eventDTO = new EventRequestDTO(ID, TITLE,DATE_NAME, LOCATION, DESCRIPTION);
        ServiceException e = assertThrows(ServiceException.class , () -> eventService.createEvent(eventDTO));
        assertFalse(e.getMessage().contains("Duplicate entry"));
    }

    @Test
    void testViewEvent() throws DAOException, ServiceException {
        when(eventDAO.getById(ID)).thenReturn(Optional.of(getTestEvent()));
        assertEquals(getTestEventResponseDTO(), eventService.view(ID));
    }

    @Test
    void testViewNoEvent() throws DAOException {
        when(eventDAO.getById(ID)).thenReturn(Optional.empty());
        assertThrows(NoSuchEventException.class,() -> eventService.view(ID));
    }

    @Test
    void testSearchEvent() throws DAOException, ServiceException {
        when(eventDAO.getByTitle(TITLE)).thenReturn(Optional.of(getTestEvent()));
        assertEquals(getTestEventResponseDTO(), eventService.searchEvent(TITLE));
    }

    @Test
    void testSearchNoEvent() throws DAOException {
        when(eventDAO.getByTitle(TITLE)).thenReturn(Optional.empty());
        assertThrows(NoSuchEventException.class,() -> eventService.searchEvent(TITLE));
    }

    @Test
    void viewUsersEvents() throws DAOException, ServiceException {
        List<Event> events = new ArrayList<>();
        List<EventResponseDTO> eventDTOs = new ArrayList<>();
        events.add(getTestEvent());
        eventDTOs.add(getTestEventResponseDTO());
        when(eventDAO.getEventsByVisitor(ID)).thenReturn(events);
        assertIterableEquals(eventDTOs, eventService.viewUsersEvents(ID));
    }

    @Test
    void viewPastUsersEvents() throws DAOException, ServiceException {
        List<Event> events = new ArrayList<>();
        List<EventResponseDTO> eventDTOs = new ArrayList<>();
        Event testEvent = getTestEvent();
        testEvent.setDate(LocalDate.of(2010, 12, 12));
        events.add(testEvent);
        EventResponseDTO testEventResponseDTO = getTestEventResponseDTO();
        testEventResponseDTO.setDate("2010-12-12");
        eventDTOs.add(testEventResponseDTO);
        when(eventDAO.getPastEventsByVisitor(ID)).thenReturn(events);
        assertIterableEquals(eventDTOs, eventService.viewPastUsersEvents(ID));
    }

    @Test
    void viewSpeakersEvents() throws DAOException, ServiceException {
        List<Event> events = new ArrayList<>();
        List<EventResponseDTO> eventDTOs = new ArrayList<>();
        events.add(getTestEvent());
        eventDTOs.add(getTestEventResponseDTO());
        when(eventDAO.getEventsBySpeaker(ID)).thenReturn(events);
        assertIterableEquals(eventDTOs, eventService.viewSpeakersEvents(ID));
    }

    @Test
    void viewSpeakersPastEvents() throws DAOException, ServiceException {
        List<Event> events = new ArrayList<>();
        List<EventResponseDTO> eventDTOs = new ArrayList<>();
        Event testEvent = getTestEvent();
        testEvent.setDate(LocalDate.of(2010, 12, 12));
        events.add(testEvent);
        EventResponseDTO testEventResponseDTO = getTestEventResponseDTO();
        testEventResponseDTO.setDate("2010-12-12");
        eventDTOs.add(testEventResponseDTO);
        when(eventDAO.getPastEventsBySpeaker(ID)).thenReturn(events);
        assertIterableEquals(eventDTOs, eventService.viewSpeakersPastEvents(ID));
    }

    @Test
    void viewSortedEventsByUser() throws DAOException, ServiceException {
        List<Event> events = new ArrayList<>();
        List<EventResponseDTO> eventDTOs = new ArrayList<>();
        events.add(getTestEvent());
        eventDTOs.add(getTestEventResponseDTO());
        when(eventDAO.getSortedEvents(UPCOMING, "title", ASC)).thenReturn(events);
        assertIterableEquals(eventDTOs, eventService.viewSortedEventsByUser("title", ASC));
    }

    @Test
    void viewSortedEventsByModerator() throws DAOException, ServiceException {
        List<Event> events = new ArrayList<>();
        List<EventResponseDTO> eventDTOs = new ArrayList<>();
        events.add(getTestEvent());
        eventDTOs.add(getTestFullEventResponseDTO());
        when(eventDAO.getSortedEvents(PASSED, "date", DESC)).thenReturn(events);
        assertIterableEquals(eventDTOs, eventService.viewSortedEventsByModerator(PASSED, "date", DESC));
    }

    @Test
    void testEditEvent() throws ServiceException, DAOException {
        doNothing().when(eventDAO).update(isA(Event.class));
        assertEquals(getTestEventResponseDTO(), eventService.editEvent(getTestEventRequestDTO()));
    }

    @Test
    void setVisitorsCount() throws DAOException {
        doNothing().when(eventDAO).setVisitorsCount(isA(long.class), isA(int.class));
        assertDoesNotThrow(() -> eventService.setVisitorsCount(ID, VISITORS));
    }

    @Test
    void deleteEvent() throws DAOException {
        doNothing().when(eventDAO).delete(isA(long.class));
        assertDoesNotThrow(() -> eventService.delete(ID));
    }

    private EventRequestDTO getTestEventRequestDTO() {
        return new EventRequestDTO(ID, TITLE, DATE_NAME, LOCATION, DESCRIPTION);
    }

    private EventResponseDTO getTestEventResponseDTO() {
        return new EventResponseDTO(ID, TITLE, DATE_NAME, LOCATION, DESCRIPTION);
    }

    private EventResponseDTO getTestFullEventResponseDTO() {
        return new EventResponseDTO(ID, TITLE, DATE_NAME, LOCATION, REPORTS, REGISTRATIONS, VISITORS);
    }

    private Event getTestEvent() {
        return new Event.EventBuilder()
                .setId(ID)
                .setTitle(TITLE)
                .setDate(DATE)
                .setLocation(LOCATION)
                .setDescription(DESCRIPTION)
                .setReports(REPORTS)
                .setRegistrations(REGISTRATIONS)
                .setVisitors(VISITORS)
                .get();
    }
}