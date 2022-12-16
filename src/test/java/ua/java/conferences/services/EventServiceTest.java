package ua.java.conferences.services;

import org.junit.jupiter.api.Test;
import ua.java.conferences.dao.EventDAO;
import ua.java.conferences.dto.EventDTO;
import ua.java.conferences.entities.Event;
import ua.java.conferences.entities.role.Role;
import ua.java.conferences.exceptions.*;
import ua.java.conferences.services.implementation.EventServiceImpl;
import ua.java.conferences.utils.sorting.Sorting;

import java.sql.SQLException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static ua.java.conferences.Constants.*;
import static ua.java.conferences.exceptions.constants.Message.*;

class EventServiceTest {

    private final EventDAO eventDAO = mock(EventDAO.class);

    private final EventService eventService = new EventServiceImpl(eventDAO);

    @Test
    void testAddEvent() throws DAOException {
        doNothing().when(eventDAO).add(isA(Event.class));
        assertDoesNotThrow(() -> eventService.addEvent(getTestEventDTO()));
    }

    @Test
    void testAddIncorrectTitle() throws DAOException {
        doNothing().when(eventDAO).add(isA(Event.class));
        EventDTO eventDTO = getTestEventDTO();
        eventDTO.setTitle(INCORRECT_TITLE);
        IncorrectFormatException e = assertThrows(IncorrectFormatException.class , () -> eventService.addEvent(eventDTO));
        assertEquals(ENTER_CORRECT_TITLE, e.getMessage());
    }

    @Test
    void testAddIncorrectDate() throws DAOException {
        doNothing().when(eventDAO).add(isA(Event.class));
        EventDTO eventDTO = getTestEventDTO();
        eventDTO.setDate(INCORRECT_DATE_NAME);
        IncorrectFormatException e = assertThrows(IncorrectFormatException.class , () -> eventService.addEvent(eventDTO));
        assertEquals(ENTER_VALID_DATE, e.getMessage());
    }

    @Test
    void testAddIncorrectLocation() throws DAOException {
        doNothing().when(eventDAO).add(isA(Event.class));
        EventDTO eventDTO = getTestEventDTO();
        eventDTO.setLocation(INCORRECT_LOCATION);
        IncorrectFormatException e = assertThrows(IncorrectFormatException.class , () -> eventService.addEvent(eventDTO));
        assertEquals(ENTER_CORRECT_LOCATION, e.getMessage());
    }

    @Test
    void testAddIncorrectDescription() throws DAOException {
        doNothing().when(eventDAO).add(isA(Event.class));
        EventDTO eventDTO = getTestEventDTO();
        eventDTO.setDescription(INCORRECT_DESCRIPTION);
        IncorrectFormatException e = assertThrows(IncorrectFormatException.class , () -> eventService.addEvent(eventDTO));
        assertEquals(ENTER_CORRECT_DESCRIPTION, e.getMessage());
    }

    @Test
    void testAddDuplicateTitle() throws DAOException {
        doThrow(new DAOException(new SQLException("Duplicate entry"))).when(eventDAO).add(isA(Event.class));
        assertThrows(DuplicateTitleException.class, () -> eventService.addEvent(getTestEventDTO()));
    }

    @Test
    void testAddDbIsDown() throws DAOException {
        doThrow(new DAOException(new SQLException())).when(eventDAO).add(isA(Event.class));
        ServiceException e = assertThrows(ServiceException.class , () -> eventService.addEvent(getTestEventDTO()));
        assertFalse(e.getMessage().contains("Duplicate entry"));
    }

    @Test
    void testGetById() throws DAOException, ServiceException {
        when(eventDAO.getById(ID_VALUE)).thenReturn(Optional.of(getTestEvent()));
        assertEquals(getTestEventDTO(), eventService.getById(String.valueOf(ID_VALUE)));
    }

    @Test
    void testGetByIdNoEvent() throws DAOException {
        when(eventDAO.getById(ID_VALUE)).thenReturn(Optional.empty());
        assertThrows(NoSuchEventException.class,() -> eventService.getById(String.valueOf(ID_VALUE)));
    }

    @Test
    void testGetByTitle() throws DAOException, ServiceException {
        when(eventDAO.getByTitle(TITLE)).thenReturn(Optional.of(getTestEvent()));
        assertEquals(getTestEventDTO(), eventService.getByTitle(TITLE));
    }

    @Test
    void testGetByTitleNoEvent() throws DAOException {
        when(eventDAO.getByTitle(TITLE)).thenReturn(Optional.empty());
        assertThrows(NoSuchEventException.class,() -> eventService.getByTitle(TITLE));
    }

    @Test
    void testGetAll() throws DAOException, ServiceException {
        when(eventDAO.getAll()).thenReturn(List.of(getTestEvent()));
        assertIterableEquals(List.of(getTestEventDTO()), eventService.getAll());
    }

    @Test
    void testGetSorted() throws DAOException, ServiceException {
        List<Event> events = new ArrayList<>();
        List<EventDTO> eventDTOs = new ArrayList<>();
        events.add(getTestEvent());
        eventDTOs.add(getTestEventDTO());
        Sorting sorting = Sorting.getEventSorting(PASSED, ID, ASC);
        when(eventDAO.getSorted(sorting, ZERO, TEN)).thenReturn(events);
        assertIterableEquals(eventDTOs, eventService.getSorted(sorting, "0", "10"));
    }

    @Test
    void testGetSortedWrongOffset() throws DAOException, ServiceException {
        List<Event> events = new ArrayList<>();
        events.add(getTestEvent());
        Sorting sorting = Sorting.getEventSorting(PASSED, ID, ASC);
        when(eventDAO.getSorted(sorting, ZERO, TEN)).thenReturn(events);
        assertThrows(ServiceException.class, () -> eventService.getSorted(sorting, "a", "10"));
    }

    @Test
    void testGetSortedWrongRecords() throws DAOException, ServiceException {
        List<Event> events = new ArrayList<>();
        events.add(getTestEvent());
        Sorting sorting = Sorting.getEventSorting(PASSED, ID, ASC);
        when(eventDAO.getSorted(sorting, ZERO, TEN)).thenReturn(events);
        assertThrows(ServiceException.class, () -> eventService.getSorted(sorting, "0", null));
    }

    @Test
    void testGetSortedByUser() throws DAOException, ServiceException {
        List<Event> events = new ArrayList<>();
        List<EventDTO> eventDTOs = new ArrayList<>();
        events.add(getTestEvent());
        eventDTOs.add(getTestShortEventDTO());
        Sorting sorting = Sorting.getEventSorting(PASSED, ID, ASC);
        when(eventDAO.getSortedByUser(ID_VALUE, sorting, ZERO, TEN, ROLE_VISITOR)).thenReturn(events);
        assertIterableEquals(eventDTOs, eventService.getSortedByUser(ID_VALUE, sorting, "0", "10", ROLE_VISITOR));
    }

    @Test
    void testGetNumberOfRecords() throws DAOException, ServiceException {
        Sorting sorting = Sorting.getEventSorting(UPCOMING, ID, ASC);
        when(eventDAO.getNumberOfRecords(ZERO, sorting,  Role.MODERATOR.name())).thenReturn(TEN);
        assertEquals(TEN, eventService.getNumberOfRecords(sorting));
    }

    @Test
    void testGetNumberOfRecordsByUser() throws DAOException, ServiceException {
        Sorting sorting = Sorting.getEventSorting(UPCOMING, ID, ASC);
        when(eventDAO.getNumberOfRecords(ONE, sorting,  ROLE_VISITOR)).thenReturn(THREE);
        assertEquals(THREE, eventService.getNumberOfRecordsByUser(ONE, sorting,  ROLE_VISITOR));
    }

    @Test
    void testUpdate() throws DAOException {
        doNothing().when(eventDAO).update(isA(Event.class));
        assertDoesNotThrow(() -> eventService.update(getTestEventDTO()));
    }

    @Test
    void testUpdateIncorrectTitle() throws DAOException {
        doNothing().when(eventDAO).update(isA(Event.class));
        EventDTO eventDTO = getTestEventDTO();
        eventDTO.setTitle(INCORRECT_TITLE);
        IncorrectFormatException e = assertThrows(IncorrectFormatException.class , () -> eventService.update(eventDTO));
        assertEquals(ENTER_CORRECT_TITLE, e.getMessage());
    }

    @Test
    void testUpdateIncorrectDate() throws DAOException {
        doNothing().when(eventDAO).update(isA(Event.class));
        EventDTO eventDTO = getTestEventDTO();
        eventDTO.setDate(INCORRECT_DATE_NAME);
        IncorrectFormatException e = assertThrows(IncorrectFormatException.class , () -> eventService.update(eventDTO));
        assertEquals(ENTER_VALID_DATE, e.getMessage());
    }

    @Test
    void testUpdateIncorrectLocation() throws DAOException {
        doNothing().when(eventDAO).update(isA(Event.class));
        EventDTO eventDTO = getTestEventDTO();
        eventDTO.setLocation(INCORRECT_LOCATION);
        IncorrectFormatException e = assertThrows(IncorrectFormatException.class , () -> eventService.update(eventDTO));
        assertEquals(ENTER_CORRECT_LOCATION, e.getMessage());
    }

    @Test
    void testUpdateIncorrectDescription() throws DAOException {
        doNothing().when(eventDAO).update(isA(Event.class));
        EventDTO eventDTO = getTestEventDTO();
        eventDTO.setDescription(INCORRECT_DESCRIPTION);
        IncorrectFormatException e = assertThrows(IncorrectFormatException.class , () -> eventService.update(eventDTO));
        assertEquals(ENTER_CORRECT_DESCRIPTION, e.getMessage());
    }

    @Test
    void testUpdateDuplicateTitle() throws DAOException {
        doThrow(new DAOException(new SQLException("Duplicate entry"))).when(eventDAO).update(isA(Event.class));
        assertThrows(DuplicateTitleException.class, () -> eventService.update(getTestEventDTO()));
    }

    @Test
    void setVisitorsCount() throws DAOException {
        doNothing().when(eventDAO).setVisitorsCount(isA(long.class), isA(int.class));
        assertDoesNotThrow(() -> eventService.setVisitorsCount(String.valueOf(ID_VALUE), String.valueOf(VISITORS)));
    }

    @Test
    void setVisitorsCountWrongEventId() throws DAOException {
        doNothing().when(eventDAO).setVisitorsCount(isA(long.class), isA(int.class));
        assertThrows(NoSuchEventException.class, () -> eventService.setVisitorsCount(NAME, String.valueOf(VISITORS)));
    }

    @Test
    void setVisitorsCountWrongNumber() throws DAOException {
        doNothing().when(eventDAO).setVisitorsCount(isA(long.class), isA(int.class));
        assertThrows(ServiceException.class, () -> eventService.setVisitorsCount(String.valueOf(ID_VALUE), NAME));
    }

    @Test
    void deleteEvent() throws DAOException {
        doNothing().when(eventDAO).delete(isA(long.class));
        assertDoesNotThrow(() -> eventService.delete(String.valueOf(ID_VALUE)));
    }

    private EventDTO getTestShortEventDTO() {
        return new EventDTO.Builder()
                .setId(ID_VALUE)
                .setTitle(TITLE)
                .setDate(DATE_NAME)
                .setLocation(LOCATION)
                .setDescription(DESCRIPTION)
                .get();
    }

    private EventDTO getTestEventDTO() {
        return new EventDTO.Builder()
                .setId(ID_VALUE)
                .setTitle(TITLE)
                .setDate(DATE_NAME)
                .setLocation(LOCATION)
                .setDescription(DESCRIPTION)
                .setReports(REPORTS)
                .setRegistrations(REGISTRATIONS)
                .setVisitors(VISITORS)
                .get();
    }

    private Event getTestEvent() {
        return new Event.Builder()
                .setId(ID_VALUE)
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