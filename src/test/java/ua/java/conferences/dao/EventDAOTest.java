package ua.java.conferences.dao;

import org.junit.jupiter.api.*;
import ua.java.conferences.entities.*;
import ua.java.conferences.entities.role.Role;
import ua.java.conferences.exceptions.*;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static ua.java.conferences.Constants.*;
import static ua.java.conferences.dao.DAOTestUtils.*;
import static ua.java.conferences.utils.QueryBuilderUtil.*;

class EventDAOTest {

    @BeforeEach
    void clearDB() throws FileNotFoundException, SQLException {
        createEmptyDB();
    }

    @Test
    void testAdd() {
        assertDoesNotThrow(() -> eventDAO.add(getTestEvent()));
    }

    @Test
    void testTwoEvents() throws DAOException {
        eventDAO.add(getTestEvent());
        Event event = getTestEvent();
        event.setTitle(ANOTHER_TITLE);
        assertDoesNotThrow(() -> eventDAO.add(event));
    }

    @Test
    void testAddTwice() throws DAOException {
        eventDAO.add(getTestEvent());
        DAOException exception = assertThrows((DAOException.class), () -> eventDAO.add(getTestEvent()));
        assertTrue(exception.getMessage().contains(DUPLICATE));
    }

    @Test
    void testGetById() throws DAOException {
        eventDAO.add(getTestEvent());

        Event event = eventDAO.getById(ID_VALUE).orElse(null);
        assertNotNull(event);
        assertEquals(event, getTestEvent());
    }

    @Test
    void testGetByIdNoEvent() throws DAOException {
        assertNull(eventDAO.getById(ID_VALUE).orElse(null));
    }

    @Test
    void testGetByTitle() throws DAOException {
        eventDAO.add(getTestEvent());

        Event event = eventDAO.getByTitle(TITLE).orElse(null);
        assertNotNull(event);
        assertEquals(event, getTestEvent());
    }

    @Test
    void testGetByTitleNoEvent() throws DAOException {
        assertNull(eventDAO.getByTitle(TITLE).orElse(null));
    }


    @Test
    void testGetAll() throws DAOException {
        eventDAO.add(getTestEvent());

        List<Event> events = eventDAO.getAll();
        assertTrue(events.contains(getTestEvent()));
        assertEquals(ONE, events.size());
    }

    @Test
    void testGetAllMoreUsers() throws DAOException {
        eventDAO.add(getTestEvent());
        Event event = getTestEvent();
        event.setTitle(ANOTHER_TITLE);
        eventDAO.add(event);
        event.setTitle(ANOTHER_TITLE + ANOTHER_TITLE);
        eventDAO.add(event);

        List<Event> events = eventDAO.getAll();
        assertTrue(events.contains(getTestEvent()));
        assertEquals(THREE, events.size());
    }

    @Test
    void testUpdate() throws DAOException {
        eventDAO.add(getTestEvent());

        assertDoesNotThrow(() -> eventDAO.update(getTestEvent()));
    }

    @Test
    void testUpdateCheckUpdated() throws DAOException {
        eventDAO.add(getTestEvent());

        Event event = getTestEvent();
        event.setTitle(ANOTHER_TITLE);
        event.setLocation(ANOTHER_TITLE);
        eventDAO.update(event);

        Event resultEvent = eventDAO.getById(ID_VALUE).orElse(null);
        assertNotNull(resultEvent);
        assertEquals(resultEvent.getTitle(), event.getTitle());
        assertEquals(resultEvent.getLocation(), event.getLocation());
    }


    @Test
    void testUpdateDuplicateTitle() throws DAOException {
        eventDAO.add(getTestEvent());

        Event event = getTestEvent();
        event.setId(2);
        event.setTitle(ANOTHER_TITLE);
        eventDAO.add(event);

        event.setTitle(getTestEvent().getTitle());
        DAOException exception = assertThrows((DAOException.class), () -> eventDAO.update(event));
        assertTrue(exception.getMessage().contains(DUPLICATE));
    }

    @Test
    void testDelete() throws DAOException {
        eventDAO.add(getTestEvent());

        assertDoesNotThrow(() -> eventDAO.delete(ID_VALUE));
        List<Event> events = eventDAO.getAll();
        assertEquals(ZERO, events.size());
    }

    @Test
    void testDeleteNoEvent() {
        assertDoesNotThrow(() -> eventDAO.delete(ID_VALUE));
    }

    @Test
    void testDeleteWithReportAndRegistration() throws DAOException {
        eventDAO.add(getTestEvent());
        userDAO.add(getTestUser());
        reportDAO.add(getTestReport());
        userDAO.registerForEvent(ID_VALUE, ID_VALUE);

        assertDoesNotThrow(() -> eventDAO.delete(ID_VALUE));
        assertEquals(ZERO, eventDAO.getAll().size());

        assertFalse(userDAO.isRegistered(ID_VALUE, ID_VALUE));
        assertEquals(ZERO, reportDAO.getAll().size());
    }


    @Test
    void testSetVisitors() throws DAOException {
        eventDAO.add(getTestEvent());
        assertDoesNotThrow(() -> eventDAO.setVisitorsCount(ID_VALUE, VISITORS));

        Event resultEvent = eventDAO.getByTitle(TITLE).orElse(getTestEvent());
        assertEquals(VISITORS, resultEvent.getVisitors());
    }

    @Test
    void testGetAllUpcoming() throws DAOException {
        List<Event> events = getRandomEvents();
        for (int i = 0; i < 5; i++) {
            eventDAO.add(events.get(i));
        }
        String query = eventQueryBuilder().setDateFilter(UPCOMING).getQuery();
        events = events.stream().filter(event -> event.getDate().isAfter(LocalDate.now())).collect(Collectors.toList());
        List<Event> eventList = eventDAO.getSorted(query);
        assertIterableEquals(events, eventList);
    }

    @Test
    void testGetAllUpcomingDesc() throws DAOException {
        List<Event> events = getRandomEvents();
        for (int i = 0; i < 5; i++) {
            eventDAO.add(events.get(i));
        }
        events = events.stream()
                .filter(event -> event.getDate().isAfter(LocalDate.now()))
                .sorted(Comparator.comparing(Event::getId).reversed())
                .collect(Collectors.toList());
        String query = eventQueryBuilder().setDateFilter(UPCOMING).setOrder(DESC).getQuery();
        List<Event> eventList = eventDAO.getSorted(query);
        assertIterableEquals(events, eventList);
    }

    @Test
    void testGetAllPassed() throws DAOException {
        List<Event> events = getRandomEvents();
        for (int i = 0; i < 5; i++) {
            eventDAO.add(events.get(i));
        }
        events = events.stream()
                .filter(event -> event.getDate().isBefore(LocalDate.now()))
                .collect(Collectors.toList());
        String query = eventQueryBuilder().setDateFilter(PASSED).setOrder(ASC).getQuery();
        List<Event> eventList = eventDAO.getSorted(query);
        assertIterableEquals(events, eventList);
    }

    @Test
    void testGetAllPassedDesc() throws DAOException {
        List<Event> events = getRandomEvents();
        for (int i = 0; i < 5; i++) {
            eventDAO.add(events.get(i));
        }
        events = events.stream()
                .filter(event -> event.getDate().isBefore(LocalDate.now()))
                .sorted(Comparator.comparing(Event::getId).reversed())
                .collect(Collectors.toList());
        String query = eventQueryBuilder().setDateFilter(PASSED).setOrder(DESC).getQuery();
        List<Event> eventList = eventDAO.getSorted(query);
        assertIterableEquals(events, eventList);
    }

    @Test
    void testSortedByTitle() throws DAOException {
        List<Event> events = getRandomEvents();
        for (int i = 0; i < 5; i++) {
            eventDAO.add(events.get(i));
        }
        events = events.stream()
                .filter(event -> event.getDate().isAfter(LocalDate.now()))
                .sorted(Comparator.comparing(Event::getTitle))
                .collect(Collectors.toList());
        String query = eventQueryBuilder()
                .setDateFilter(UPCOMING)
                .setSortField(TITLE_FIELD)
                .getQuery();
        List<Event> eventList = eventDAO.getSorted(query);
        assertIterableEquals(events, eventList);
    }

    @Test
    void testPagination() throws DAOException {
        List<Event> events = getRandomFutureEvents();
        for (int i = 0; i < 5; i++) {
            eventDAO.add(events.get(i));
        }
        events = events.stream()
                .limit(THREE)
                .collect(Collectors.toList());
        String query = eventQueryBuilder()
                .setDateFilter(UPCOMING)
                .setLimits("0", "3")
                .getQuery();
        List<Event> eventList = eventDAO.getSorted(query);
        assertIterableEquals(events, eventList);
    }

    @Test
    void testPaginationNotZeroPosition() throws DAOException {
        List<Event> events = getRandomFutureEvents();
        for (int i = 0; i < 5; i++) {
            eventDAO.add(events.get(i));
        }
        events = events.stream()
                .sorted(Comparator.comparing(Event::getTitle))
                .skip(THREE)
                .limit(THREE)
                .collect(Collectors.toList());
        String query = eventQueryBuilder()
                .setSortField(TITLE_FIELD)
                .setLimits("3", "3")
                .getQuery();
        List<Event> eventList = eventDAO.getSorted(query);
        assertIterableEquals(events, eventList);
    }

    @Test
    void testVisitorsEvents() throws DAOException {
        List<Event> events = getRandomEvents();
        userDAO.add(getTestUser());
        for (int i = 0; i < 5; i++) {
            eventDAO.add(events.get(i));
            userDAO.registerForEvent(ID_VALUE, events.get(i).getId());
        }
        events = events.stream().filter(event -> event.getDate().isAfter(LocalDate.now())).collect(Collectors.toList());
        String query = visitorEventQueryBuilder()
                .setUserIdFilter(ID_VALUE)
                .setDateFilter(UPCOMING)
                .getQuery();
        List<Event> eventList = eventDAO.getSortedByUser(query, Role.VISITOR);
        assertIterableEquals(events, eventList);
    }

    @Test
    void testVisitorsPastEvents() throws DAOException {
        List<Event> events = getRandomEvents();
        userDAO.add(getTestUser());
        for (int i = 0; i < 5; i++) {
            eventDAO.add(events.get(i));
            userDAO.registerForEvent(ID_VALUE, events.get(i).getId());
        }
        events = events.stream().filter(event -> event.getDate().isBefore(LocalDate.now())).collect(Collectors.toList());
        String query = visitorEventQueryBuilder()
                .setUserIdFilter(ID_VALUE)
                .setDateFilter(PASSED)
                .getQuery();
        List<Event> eventList = eventDAO.getSortedByUser(query, Role.VISITOR);
        assertIterableEquals(events, eventList);
    }


    @Test
    void testSpeakersEvents() throws DAOException {
        List<Event> events = getRandomEvents();
        userDAO.add(getTestUser());
        for (int i = 0; i < 5; i++) {
            eventDAO.add(events.get(i));
            Report report = getTestReport();
            report.setEvent(events.get(i));
            reportDAO.add(report);
        }
        events = events.stream().filter(event -> event.getDate().isAfter(LocalDate.now())).collect(Collectors.toList());
        String query = eventQueryBuilder()
                .setUserIdFilter(ID_VALUE)
                .setDateFilter(UPCOMING)
                .getQuery();
        List<Event> eventList = eventDAO.getSortedByUser(query, Role.SPEAKER);
        assertIterableEquals(events, eventList);
    }

    @Test
    void testGetNumberOfRecords() throws DAOException {
        List<Event> events = getRandomEvents();
        for (int i = 0; i < 5; i++) {
            eventDAO.add(events.get(i));
        }
        events = events.stream().filter(event -> event.getDate().isAfter(LocalDate.now())).collect(Collectors.toList());
        String query = eventQueryBuilder()
                .setDateFilter(UPCOMING)
                .getRecordQuery();
        int numberOfRecords = eventDAO.getNumberOfRecords(query, Role.MODERATOR);
        assertEquals(events.size(), numberOfRecords);
    }

    @Test
    void testGetNumberOfRecordsBySpeaker() throws DAOException {
        List<Event> events = getRandomEvents();
        userDAO.add(getTestUser());
        for (int i = 0; i < 5; i++) {
            eventDAO.add(events.get(i));
            Report report = getTestReport();
            report.setEvent(events.get(i));
            reportDAO.add(report);
        }
        events = events.stream().filter(event -> event.getDate().isAfter(LocalDate.now())).collect(Collectors.toList());
        String query = eventQueryBuilder()
                .setUserIdFilter(ID_VALUE)
                .setDateFilter(UPCOMING)
                .getRecordQuery();
        int numberOfRecords = eventDAO.getNumberOfRecords(query, Role.SPEAKER);
        assertEquals(events.size(), numberOfRecords);
    }

    private List<Event> getRandomEvents() {
        List<Event> events = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            events.add(getRandomEvent(i));
        }
        return events;
    }

    private List<Event> getRandomFutureEvents() {
        List<Event> events = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Event randomEvent = getRandomEvent(i);
            randomEvent.setDate(LocalDate.now().plusDays(ONE));
            events.add(randomEvent);
        }
        return events;
    }

    private Event getRandomEvent(int i) {
        Event event = getTestEvent();
        event.setId(i + 1);
        event.setTitle(new Random().nextInt(100) + TITLE + i);
        event.setDate(randomDate());
        return event;
    }

    private LocalDate randomDate() {
        long minDay = LocalDate.of(2010, 1, 1).toEpochDay();
        long maxDay = LocalDate.of(2050, 12, 31).toEpochDay();
        long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
        return LocalDate.ofEpochDay(randomDay);
    }
}