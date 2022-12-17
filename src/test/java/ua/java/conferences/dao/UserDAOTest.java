package ua.java.conferences.dao;

import org.junit.jupiter.api.*;
import ua.java.conferences.entities.*;
import ua.java.conferences.entities.role.Role;
import ua.java.conferences.exceptions.*;

import java.io.*;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static ua.java.conferences.dao.DAOTestUtils.*;
import static ua.java.conferences.Constants.*;
import static ua.java.conferences.utils.QueryBuilderUtil.*;

class UserDAOTest {

    @BeforeEach
    void clearDB() throws FileNotFoundException, SQLException {
        createEmptyDB();
    }

    @Test
    void testAdd() {
        assertDoesNotThrow(() -> userDAO.add(getTestUser()));
    }

    @Test
    void testTwoUsers() throws DAOException {
        userDAO.add(getTestUser());
        User testUser2 = getTestUser();
        testUser2.setEmail(ANOTHER_EMAIL);
        assertDoesNotThrow(() -> userDAO.add(testUser2));
    }

    @Test
    void testAddTwice() throws DAOException {
        userDAO.add(getTestUser());
        DAOException exception = assertThrows((DAOException.class), () -> userDAO.add(getTestUser()));
        assertTrue(exception.getMessage().contains(DUPLICATE));
    }

    @Test
    void testGetById() throws DAOException {
        userDAO.add(getTestUser());

        User resultUser = userDAO.getById(ID_VALUE).orElse(null);
        assertNotNull(resultUser);
        assertEquals(resultUser, getTestUser());
    }

    @Test
    void testGetByIdNoUser() throws DAOException {
        assertNull(userDAO.getById(ID_VALUE).orElse(null));
    }

    @Test
    void testGetByEmail() throws DAOException {
        userDAO.add(getTestUser());

        User resultUser = userDAO.getByEmail(EMAIL).orElse(null);
        assertNotNull(resultUser);
        assertEquals(resultUser, getTestUser());
    }

    @Test
    void testGetByEmailNoUser() throws DAOException {
        assertNull(userDAO.getByEmail(EMAIL).orElse(null));
    }

    @Test
    void testGetAll() throws DAOException {
        userDAO.add(getTestUser());

        List<User> users = userDAO.getAll();
        assertTrue(users.contains(getTestUser()));
        assertEquals(ONE, users.size());
    }

    @Test
    void testGetAllMoreUsers() throws DAOException {
        userDAO.add(getTestUser());
        User testUser2 = getTestUser();
        testUser2.setEmail(ANOTHER_EMAIL);
        userDAO.add(testUser2);
        testUser2.setEmail(ANOTHER_EMAIL + ANOTHER_EMAIL);
        userDAO.add(testUser2);

        List<User> users = userDAO.getAll();
        assertTrue(users.contains(getTestUser()));
        assertEquals(THREE, users.size());
    }

    @Test
    void testUpdate() throws DAOException {
        userDAO.add(getTestUser());

        assertDoesNotThrow(() -> userDAO.update(getTestUser()));
    }

    @Test
    void testUpdateCheckUpdated() throws DAOException {
        userDAO.add(getTestUser());

        User testUser = getTestUser();
        testUser.setEmail(ANOTHER_EMAIL);
        testUser.setName(SURNAME);
        userDAO.update(testUser);

        User resultUser = userDAO.getById(ID_VALUE).orElse(null);
        assertNotNull(resultUser);
        assertEquals(resultUser.getEmail(), testUser.getEmail());
        assertEquals(resultUser.getName(), testUser.getName());
    }

    @Test
    void testUpdateDuplicateEmail() throws DAOException {
        userDAO.add(getTestUser());

        User testUser2 = getTestUser();
        testUser2.setId(2);
        testUser2.setEmail(ANOTHER_EMAIL);
        userDAO.add(testUser2);

        testUser2.setEmail(EMAIL);
        DAOException exception = assertThrows((DAOException.class), () -> userDAO.update(testUser2));
        assertTrue(exception.getMessage().contains(DUPLICATE));
    }

    @Test
    void testDelete() throws DAOException {
        userDAO.add(getTestUser());

        assertDoesNotThrow(() -> userDAO.delete(ID_VALUE));
        List<User> users = userDAO.getAll();
        assertEquals(ZERO, users.size());
    }

    @Test
    void testDeleteNoUser() {
        assertDoesNotThrow(() -> userDAO.delete(ID_VALUE));
    }

    @Test
    void testUpdatePassword() throws DAOException {
        User testUser = getTestUser();
        userDAO.add(testUser);

        testUser.setPassword(ANOTHER_PASSWORD);
        userDAO.updatePassword(testUser);

        testUser = userDAO.getById(ID_VALUE).orElse(null);
        assertNotNull(testUser);
        assertEquals(ANOTHER_PASSWORD, testUser.getPassword());
    }

    @Test
    void testRegisterForEvent() throws DAOException {
        userDAO.add(getTestUser());
        eventDAO.add(getTestEvent());
        userDAO.registerForEvent(ID_VALUE, ID_VALUE);
        assertTrue(userDAO.isRegistered(ID_VALUE, ID_VALUE));
    }

    @Test
    void testBadRegistrations() throws DAOException {
        userDAO.add(getTestUser());

        assertThrows((DAOException.class), () -> userDAO.registerForEvent(ID_VALUE, ID_VALUE));
    }

    @Test
    void testBadRegistrations2() throws DAOException {
        userDAO.add(getTestUser());
        eventDAO.add(getTestEvent());

        assertFalse(userDAO.isRegistered(ID_VALUE, ID_VALUE));
    }

    @Test
    void testCancelRegistration() throws DAOException {
        userDAO.add(getTestUser());
        eventDAO.add(getTestEvent());

        assertDoesNotThrow(() -> userDAO.registerForEvent(ID_VALUE, ID_VALUE));
        assertTrue(userDAO.isRegistered(ID_VALUE, ID_VALUE));
        assertDoesNotThrow(() -> userDAO.cancelRegistration(ID_VALUE, ID_VALUE));
        assertFalse(userDAO.isRegistered(ID_VALUE, ID_VALUE));
    }


    @Test
    void testCancelRegistrationForNoRegistered() throws DAOException {
        userDAO.add(getTestUser());
        eventDAO.add(getTestEvent());

        assertDoesNotThrow(() -> userDAO.cancelRegistration(ID_VALUE, ID_VALUE));
        assertFalse(userDAO.isRegistered(ID_VALUE, ID_VALUE));
    }

    @Test
    void testCancelRegistrationForNoEvent() throws DAOException {
        userDAO.add(getTestUser());

        assertDoesNotThrow(() -> userDAO.cancelRegistration(ID_VALUE, ID_VALUE));
        assertFalse(userDAO.isRegistered(ID_VALUE, ID_VALUE));
    }

    @Test
    void testCancelRegistrationForNoUserAndEvent() throws DAOException {
        assertDoesNotThrow(() -> userDAO.cancelRegistration(ID_VALUE, ID_VALUE));
        assertFalse(userDAO.isRegistered(ID_VALUE, ID_VALUE));
    }

    @Test
    void testRoleMethods() throws DAOException {
        User testUser = getTestUser();
        userDAO.add(testUser);

        testUser = userDAO.getById(ID_VALUE).orElse(null);
        assertNotNull(testUser);
        assertEquals(Role.VISITOR.getValue(), testUser.getRoleId());

        userDAO.setUserRole(EMAIL, Role.ADMIN);
        testUser = userDAO.getById(getTestUser().getId()).orElse(null);
        assertNotNull(testUser);
        assertEquals(Role.ADMIN.getValue(), testUser.getRoleId());
    }

    @Test
    void testGetAllSorted() throws DAOException {
        userDAO.add(getTestUser());
        List<User> sorted = userDAO.getSorted(userQueryBuilder().getQuery());
        assertFalse(sorted.isEmpty());
        assertEquals(ONE, sorted.size());
    }

    @Test
    void testGetAllSortedByName() throws DAOException {
        List<User> users = getRandomUsers();
        for (int i = 0; i < 10; i++) {
            userDAO.add(users.get(i));
        }
        users.sort(Comparator.comparing(User::getName));
        List<User> sorted = userDAO.getSorted(userQueryBuilder().setSortField(NAME_FIELD).getQuery());
        assertIterableEquals(users, sorted);
    }

    @Test
    void testGetAllSortedByNameDesc() throws DAOException {
        List<User> users = getRandomUsers();
        for (int i = 0; i < 10; i++) {
            userDAO.add(users.get(i));
        }
        users.sort(Comparator.comparing(User::getName).reversed());
        String query = userQueryBuilder().setSortField(NAME_FIELD).setOrder(DESC).getQuery();
        List<User> sorted = userDAO.getSorted(query);
        assertIterableEquals(users, sorted);
    }

    @Test
    void testGetAllSortedByNamePagination() throws DAOException {
        List<User> users = getRandomUsers();
        for (int i = 0; i < 10; i++) {
            userDAO.add(users.get(i));
        }
        users = users.stream()
                .sorted(Comparator.comparing(User::getEmail).reversed())
                .limit(THREE)
                .collect(Collectors.toList());
        String query = userQueryBuilder()
                .setSortField(EMAIL_FIELD)
                .setLimits("0", "3")
                .setOrder(DESC)
                .getQuery();
        List<User> sorted = userDAO.getSorted(query);
        assertIterableEquals(users, sorted);
    }

    @Test
    void testGetAllSortedByNamePaginationOffsetThree() throws DAOException {
        List<User> users = getRandomUsers();
        for (int i = 0; i < 10; i++) {
            userDAO.add(users.get(i));
        }
        users = users.stream()
                .sorted(Comparator.comparing(User::getName))
                .skip(THREE)
                .limit(THREE)
                .collect(Collectors.toList());
        String query = userQueryBuilder()
                .setSortField(NAME_FIELD)
                .setLimits("3", "3")
                .getQuery();
        List<User> sorted = userDAO.getSorted(query);
        assertIterableEquals(users, sorted);
    }

    @Test
    void testGetSpeakers() throws DAOException {
        List<User> speakers = getSpeakers();
        speakers.sort(Comparator.comparing(User::getName));
        String query = userQueryBuilder()
                .setRoleFilter("3")
                .setSortField(NAME_FIELD)
                .getQuery();
        List<User> sorted = userDAO.getSorted(query);
        assertIterableEquals(speakers, sorted);
    }

    @Test
    void testGetNumberOfRecords() throws DAOException {
        List<User> speakers = getSpeakers();
        String query = userQueryBuilder()
                .setRoleFilter("3")
                .getRecordQuery();
        int numberOfRecords = userDAO.getNumberOfRecords(query);
        assertEquals(speakers.size(), numberOfRecords);
    }


    private List<User> getSpeakers() throws DAOException {
        List<User> users = getRandomUsers();
        for (int i = 0; i < 10; i++) {
            userDAO.add(users.get(i));
        }
        List<User> speakers = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            User speaker = users.get(i);
            speaker.setRoleId(THREE);
            speakers.add(speaker);
            userDAO.setUserRole(speaker.getEmail(),Role.SPEAKER);
        }
        return speakers;
    }

    private List<User> getRandomUsers() {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            users.add(getRandomUser(i));
        }
        return users;
    }

    private User getRandomUser(int i) {
        User user = getTestUser();
        user.setId(i + 1);
        user.setEmail(user.getEmail() + i);
        user.setName(user.getName() + new Random().nextInt(100));
        return user;
    }
}