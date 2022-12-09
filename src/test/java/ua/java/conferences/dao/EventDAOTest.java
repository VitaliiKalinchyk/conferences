package ua.java.conferences.dao;

import org.junit.jupiter.api.*;
import ua.java.conferences.entities.*;
import ua.java.conferences.exceptions.DAOException;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static ua.java.conferences.dao.DAOTestUtils.*;
import static ua.java.conferences.dao.mysql.constants.EventSQLQueries.*;
import static ua.java.conferences.dao.mysql.constants.SQLFields.*;

class EventDAOTest {

    @BeforeEach
    void clearDB() throws FileNotFoundException, SQLException {
        createEmptyDB();
    }

    @Test
    void testCrud() throws DAOException {
        Event testEvent = getTestEvent();
        assertDoesNotThrow(() -> eventDAO.add(testEvent));

        Event resultEvent = eventDAO.getById(testEvent.getId()).orElse(null);
        assertNotNull(resultEvent);
        assertNotEquals(0, resultEvent.getId());
        assertEquals(resultEvent.getTitle(), testEvent.getTitle());
        assertEquals(resultEvent.getDate(), testEvent.getDate());
        assertEquals(resultEvent.getLocation(), testEvent.getLocation());
        assertEquals(resultEvent.getDescription(), testEvent.getDescription());
        assertEquals(resultEvent.getVisitors(), testEvent.getVisitors());
        assertEquals(resultEvent.getReports(), testEvent.getReports());
        assertEquals(resultEvent.getRegistrations(), testEvent.getRegistrations());

        List<Event> events = eventDAO.getAll();
        assertTrue(events.contains(resultEvent));
        assertEquals(1, events.size());

        resultEvent.setTitle("Result");
        assertDoesNotThrow(() -> eventDAO.update(resultEvent));
        Event changedEvent = eventDAO.getByTitle(resultEvent.getTitle()).orElse(null);
        assertNotNull(changedEvent);
        assertEquals("Result", changedEvent.getTitle());
        assertEquals(resultEvent, changedEvent);


        assertDoesNotThrow(() -> eventDAO.delete(resultEvent.getId()));
        events = eventDAO.getAll();
        assertEquals(0, events.size());
    }

    @Test
    void testAddTwice() {
        assertDoesNotThrow(() -> eventDAO.add(getTestEvent()));
        DAOException exception = assertThrows((DAOException.class), () -> eventDAO.add(getTestEvent()));
        assertTrue(exception.getMessage().contains("Duplicate entry"));
    }

    @Test
    void testGetAbsent() throws DAOException {
        assertNull(eventDAO.getByTitle(getTestEvent().getTitle()).orElse(null));
    }

    @Test
    void testDelete() throws DAOException {
        eventDAO.add(getTestEvent());
        Event testEvent = eventDAO.getByTitle(getTestEvent().getTitle()).orElse(getTestEvent());
        reportDAO.add(getTestReport());
        userDAO.add(getTestUser());
        User testUser = userDAO.getByEmail(getTestUser().getEmail()).orElse(getTestUser());
        userDAO.registerForEvent(testUser.getId(), testEvent.getId());

        assertDoesNotThrow(() -> eventDAO.delete(testEvent.getId()));
        assertEquals(0, eventDAO.getAll().size());
        assertEquals(0, eventDAO.getEventsByVisitor(testUser.getId()).size());
        assertEquals(0, reportDAO.getAll().size());
    }

    @Test
    void testVisitors() throws DAOException {
        int visitorsCount = 100;
        Event testEvent = getTestEvent();
        eventDAO.add(testEvent);
        assertDoesNotThrow(() -> eventDAO.setVisitorsCount(testEvent.getId(), visitorsCount));

        Event resultEvent = eventDAO.getByTitle(testEvent.getTitle()).orElse(testEvent);
        assertEquals(visitorsCount, resultEvent.getVisitors());
    }

    @Test
    void testUsersEvents() throws DAOException {
        eventDAO.add(getTestEvent());
        Event testEvent = eventDAO.getByTitle(getTestEvent().getTitle()).orElse(getTestEvent());
        userDAO.add(getTestUser());
        User testUser = userDAO.getByEmail(getTestUser().getEmail()).orElse(getTestUser());
        userDAO.registerForEvent(testUser.getId(), testEvent.getId());
        List<Event> events = eventDAO.getEventsByVisitor(testUser.getId());
        assertTrue(events.contains(testEvent));
        assertEquals(1, events.size());
    }

    @Test
    void testUsersPastEvents() throws DAOException {
        Event testEvent = getTestEvent();
        testEvent.setDate(LocalDate.of(2010, 1, 1));
        eventDAO.add(testEvent);
        testEvent = eventDAO.getByTitle(getTestEvent().getTitle()).orElse(getTestEvent());
        userDAO.add(getTestUser());
        User testUser = userDAO.getByEmail(getTestUser().getEmail()).orElse(getTestUser());
        userDAO.registerForEvent(testUser.getId(), testEvent.getId());
        List<Event> events = eventDAO.getPastEventsByVisitor(testUser.getId());
        assertTrue(events.contains(testEvent));
        assertEquals(1, events.size());

        events = eventDAO.getEventsByVisitor(testUser.getId());
        assertFalse(events.contains(testEvent));
        assertEquals(0, events.size());
    }

    @Test
    void testUsersEventsWrongMethod() throws DAOException {
        eventDAO.add(getTestEvent());
        Event testEvent = eventDAO.getByTitle(getTestEvent().getTitle()).orElse(getTestEvent());
        userDAO.add(getTestUser());
        User testUser = userDAO.getByEmail(getTestUser().getEmail()).orElse(getTestUser());
        userDAO.registerForEvent(testUser.getId(), testEvent.getId());
        List<Event> events = eventDAO.getEventsBySpeaker(testUser.getId());
        assertFalse(events.contains(testEvent));
        assertEquals(0, events.size());
    }

    @Test
    void testSpeakersEvents() throws DAOException {
        Event testEvent = getTestEvent();
        testEvent.setDate(LocalDate.of(2010, 1, 1));
        eventDAO.add(testEvent);
        userDAO.add(getTestUser());
        User testUser = userDAO.getByEmail(getTestUser().getEmail()).orElse(getTestUser());
        reportDAO.add(getTestReport());
        Report testReport = reportDAO.getById(1).orElse(getTestReport());
        reportDAO.setSpeaker(testReport.getId(), testUser.getId());
        List<Event> events = eventDAO.getPastEventsBySpeaker(testUser.getId());
        assertTrue(events.contains(testEvent));
        assertEquals(1, events.size());
    }


    @Test
    void testSpeakersPastEvents() throws DAOException {
        eventDAO.add(getTestEvent());
        Event testEvent = eventDAO.getByTitle(getTestEvent().getTitle()).orElse(getTestEvent());
        userDAO.add(getTestUser());
        User testUser = userDAO.getByEmail(getTestUser().getEmail()).orElse(getTestUser());
        reportDAO.add(getTestReport());
        Report testReport = reportDAO.getById(1).orElse(getTestReport());
        reportDAO.setSpeaker(testReport.getId(), testUser.getId());
        List<Event> events = eventDAO.getEventsBySpeaker(testUser.getId());
        assertTrue(events.contains(testEvent));
        assertEquals(1, events.size());

        events = eventDAO.getEventsByVisitor(testUser.getId());
        assertFalse(events.contains(testEvent));
        assertEquals(0, events.size());
    }

    @Test
    void testSpeakersEventsWrongMethod() throws DAOException {
        eventDAO.add(getTestEvent());
        Event testEvent = eventDAO.getByTitle(getTestEvent().getTitle()).orElse(getTestEvent());
        userDAO.add(getTestUser());
        User testUser = userDAO.getByEmail(getTestUser().getEmail()).orElse(getTestUser());
        reportDAO.add(getTestReport());
        Report testReport = reportDAO.getById(1).orElse(getTestReport());
        reportDAO.setSpeaker(testReport.getId(), testUser.getId());
        List<Event> events = eventDAO.getEventsByVisitor(testUser.getId());
        assertFalse(events.contains(testEvent));
        assertEquals(0, events.size());
    }

    @Test
    void testSortedEvents() throws DAOException {
        List<Event> events = getRandomEvents();
        events.forEach(EventDAOTest::addEventToDb);
        List<Event> dbEvents = eventDAO.getSortedEvents(UPCOMING, TITLE, ASC);
        List<Event> sortedEvents = events.stream()
                .filter(event -> event.getDate().isAfter(LocalDate.now()))
                .sorted(Comparator.comparing(Event::getTitle)).collect(Collectors.toList());
        assertIterableEquals(sortedEvents,dbEvents);

        dbEvents = eventDAO.getSortedEvents(PASSED, TITLE, ASC);
        sortedEvents = events.stream()
                .filter(event -> event.getDate().isBefore(LocalDate.now()))
                .sorted(Comparator.comparing(Event::getTitle)).collect(Collectors.toList());
        assertIterableEquals(sortedEvents,dbEvents);

        dbEvents = eventDAO.getSortedEvents(PASSED, DATE, DESC);
        sortedEvents = events.stream()
                .filter(event -> event.getDate().isBefore(LocalDate.now()))
                .sorted((e1, e2) -> e2.getDate().compareTo(e1.getDate())).collect(Collectors.toList());
        assertIterableEquals(sortedEvents,dbEvents);
    }

    private static void addEventToDb(Event event) {
        try {
            eventDAO.add(event);
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Event> getRandomEvents() {
        List<Event> events = new ArrayList<>();
        for (int i = 1; i < 11; i++) {
            events.add(new Event.EventBuilder()
                    .setId(i)
                    .setTitle("title" + (20 - i))
                    .setDate(randomDate())
                    .setLocation(i + "Place")
                    .setDescription("Dummy")
                    .get());
        }
        return events;
    }

    private LocalDate randomDate() {
        long minDay = LocalDate.of(2010, 1, 1).toEpochDay();
        long maxDay = LocalDate.of(2029, 12, 31).toEpochDay();
        long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
        return LocalDate.ofEpochDay(randomDay);
    }
}