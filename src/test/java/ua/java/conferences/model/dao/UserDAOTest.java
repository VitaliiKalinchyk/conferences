package ua.java.conferences.model.dao;

import org.junit.jupiter.api.*;
import ua.java.conferences.model.entities.User;
import ua.java.conferences.model.entities.role.Role;
import ua.java.conferences.exceptions.*;

import java.io.*;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static ua.java.conferences.Constants.*;
import static ua.java.conferences.model.utils.QueryBuilderUtil.*;

class UserDAOTest {

    @BeforeEach
    void clearDB() throws FileNotFoundException, SQLException {
        DAOTestUtils.createEmptyDB();
    }

    @Test
    void testAdd() {
        assertDoesNotThrow(() -> DAOTestUtils.userDAO.add(DAOTestUtils.getTestUser()));
    }

    @Test
    void testTwoUsers() throws DAOException {
        DAOTestUtils.userDAO.add(DAOTestUtils.getTestUser());
        User testUser2 = DAOTestUtils.getTestUser();
        testUser2.setEmail(ANOTHER_EMAIL);
        assertDoesNotThrow(() -> DAOTestUtils.userDAO.add(testUser2));
    }

    @Test
    void testAddTwice() throws DAOException {
        DAOTestUtils.userDAO.add(DAOTestUtils.getTestUser());
        DAOException exception = assertThrows((DAOException.class), () -> DAOTestUtils.userDAO.add(DAOTestUtils.getTestUser()));
        assertTrue(exception.getMessage().contains(DUPLICATE));
    }

    @Test
    void testGetById() throws DAOException {
        DAOTestUtils.userDAO.add(DAOTestUtils.getTestUser());

        User resultUser = DAOTestUtils.userDAO.getById(ID_VALUE).orElse(null);
        assertNotNull(resultUser);
        Assertions.assertEquals(resultUser, DAOTestUtils.getTestUser());
    }

    @Test
    void testGetByIdNoUser() throws DAOException {
        assertNull(DAOTestUtils.userDAO.getById(ID_VALUE).orElse(null));
    }

    @Test
    void testGetByEmail() throws DAOException {
        DAOTestUtils.userDAO.add(DAOTestUtils.getTestUser());

        User resultUser = DAOTestUtils.userDAO.getByEmail(EMAIL).orElse(null);
        assertNotNull(resultUser);
        Assertions.assertEquals(resultUser, DAOTestUtils.getTestUser());
    }

    @Test
    void testGetByEmailNoUser() throws DAOException {
        assertNull(DAOTestUtils.userDAO.getByEmail(EMAIL).orElse(null));
    }

    @Test
    void testGetAll() throws DAOException {
        DAOTestUtils.userDAO.add(DAOTestUtils.getTestUser());

        List<User> users = DAOTestUtils.userDAO.getAll();
        assertTrue(users.contains(DAOTestUtils.getTestUser()));
        assertEquals(ONE, users.size());
    }

    @Test
    void testGetAllMoreUsers() throws DAOException {
        DAOTestUtils.userDAO.add(DAOTestUtils.getTestUser());
        User testUser2 = DAOTestUtils.getTestUser();
        testUser2.setEmail(ANOTHER_EMAIL);
        DAOTestUtils.userDAO.add(testUser2);
        testUser2.setEmail(ANOTHER_EMAIL + ANOTHER_EMAIL);
        DAOTestUtils.userDAO.add(testUser2);

        List<User> users = DAOTestUtils.userDAO.getAll();
        assertTrue(users.contains(DAOTestUtils.getTestUser()));
        assertEquals(THREE, users.size());
    }

    @Test
    void testUpdate() throws DAOException {
        DAOTestUtils.userDAO.add(DAOTestUtils.getTestUser());

        assertDoesNotThrow(() -> DAOTestUtils.userDAO.update(DAOTestUtils.getTestUser()));
    }

    @Test
    void testUpdateCheckUpdated() throws DAOException {
        DAOTestUtils.userDAO.add(DAOTestUtils.getTestUser());

        User testUser = DAOTestUtils.getTestUser();
        testUser.setEmail(ANOTHER_EMAIL);
        testUser.setName(SURNAME);
        DAOTestUtils.userDAO.update(testUser);

        User resultUser = DAOTestUtils.userDAO.getById(ID_VALUE).orElse(null);
        assertNotNull(resultUser);
        assertEquals(resultUser.getEmail(), testUser.getEmail());
        assertEquals(resultUser.getName(), testUser.getName());
    }

    @Test
    void testUpdateDuplicateEmail() throws DAOException {
        DAOTestUtils.userDAO.add(DAOTestUtils.getTestUser());

        User testUser2 = DAOTestUtils.getTestUser();
        testUser2.setId(2);
        testUser2.setEmail(ANOTHER_EMAIL);
        DAOTestUtils.userDAO.add(testUser2);

        testUser2.setEmail(EMAIL);
        DAOException exception = assertThrows((DAOException.class), () -> DAOTestUtils.userDAO.update(testUser2));
        assertTrue(exception.getMessage().contains(DUPLICATE));
    }

    @Test
    void testDelete() throws DAOException {
        DAOTestUtils.userDAO.add(DAOTestUtils.getTestUser());

        assertDoesNotThrow(() -> DAOTestUtils.userDAO.delete(ID_VALUE));
        List<User> users = DAOTestUtils.userDAO.getAll();
        assertEquals(ZERO, users.size());
    }

    @Test
    void testDeleteNoUser() {
        assertDoesNotThrow(() -> DAOTestUtils.userDAO.delete(ID_VALUE));
    }

    @Test
    void testUpdatePassword() throws DAOException {
        User testUser = DAOTestUtils.getTestUser();
        DAOTestUtils.userDAO.add(testUser);

        testUser.setPassword(ANOTHER_PASSWORD);
        DAOTestUtils.userDAO.updatePassword(testUser);

        testUser = DAOTestUtils.userDAO.getById(ID_VALUE).orElse(null);
        assertNotNull(testUser);
        assertEquals(ANOTHER_PASSWORD, testUser.getPassword());
    }

    @Test
    void testRegisterForEvent() throws DAOException {
        DAOTestUtils.userDAO.add(DAOTestUtils.getTestUser());
        DAOTestUtils.eventDAO.add(DAOTestUtils.getTestEvent());
        DAOTestUtils.userDAO.registerForEvent(ID_VALUE, ID_VALUE);
        Assertions.assertTrue(DAOTestUtils.userDAO.isRegistered(ID_VALUE, ID_VALUE));
    }

    @Test
    void testBadRegistrations() throws DAOException {
        DAOTestUtils.userDAO.add(DAOTestUtils.getTestUser());

        assertThrows((DAOException.class), () -> DAOTestUtils.userDAO.registerForEvent(ID_VALUE, ID_VALUE));
    }

    @Test
    void testBadRegistrations2() throws DAOException {
        DAOTestUtils.userDAO.add(DAOTestUtils.getTestUser());
        DAOTestUtils.eventDAO.add(DAOTestUtils.getTestEvent());

        Assertions.assertFalse(DAOTestUtils.userDAO.isRegistered(ID_VALUE, ID_VALUE));
    }

    @Test
    void testCancelRegistration() throws DAOException {
        DAOTestUtils.userDAO.add(DAOTestUtils.getTestUser());
        DAOTestUtils.eventDAO.add(DAOTestUtils.getTestEvent());

        assertDoesNotThrow(() -> DAOTestUtils.userDAO.registerForEvent(ID_VALUE, ID_VALUE));
        Assertions.assertTrue(DAOTestUtils.userDAO.isRegistered(ID_VALUE, ID_VALUE));
        assertDoesNotThrow(() -> DAOTestUtils.userDAO.cancelRegistration(ID_VALUE, ID_VALUE));
        Assertions.assertFalse(DAOTestUtils.userDAO.isRegistered(ID_VALUE, ID_VALUE));
    }


    @Test
    void testCancelRegistrationForNoRegistered() throws DAOException {
        DAOTestUtils.userDAO.add(DAOTestUtils.getTestUser());
        DAOTestUtils.eventDAO.add(DAOTestUtils.getTestEvent());

        assertDoesNotThrow(() -> DAOTestUtils.userDAO.cancelRegistration(ID_VALUE, ID_VALUE));
        Assertions.assertFalse(DAOTestUtils.userDAO.isRegistered(ID_VALUE, ID_VALUE));
    }

    @Test
    void testCancelRegistrationForNoEvent() throws DAOException {
        DAOTestUtils.userDAO.add(DAOTestUtils.getTestUser());

        assertDoesNotThrow(() -> DAOTestUtils.userDAO.cancelRegistration(ID_VALUE, ID_VALUE));
        Assertions.assertFalse(DAOTestUtils.userDAO.isRegistered(ID_VALUE, ID_VALUE));
    }

    @Test
    void testCancelRegistrationForNoUserAndEvent() throws DAOException {
        assertDoesNotThrow(() -> DAOTestUtils.userDAO.cancelRegistration(ID_VALUE, ID_VALUE));
        Assertions.assertFalse(DAOTestUtils.userDAO.isRegistered(ID_VALUE, ID_VALUE));
    }

    @Test
    void testRoleMethods() throws DAOException {
        User testUser = DAOTestUtils.getTestUser();
        DAOTestUtils.userDAO.add(testUser);

        testUser = DAOTestUtils.userDAO.getById(ID_VALUE).orElse(null);
        assertNotNull(testUser);
        assertEquals(Role.VISITOR.getValue(), testUser.getRoleId());

        DAOTestUtils.userDAO.setUserRole(EMAIL, Role.ADMIN);
        testUser = DAOTestUtils.userDAO.getById(DAOTestUtils.getTestUser().getId()).orElse(null);
        assertNotNull(testUser);
        assertEquals(Role.ADMIN.getValue(), testUser.getRoleId());
    }

    @Test
    void testGetAllSorted() throws DAOException {
        DAOTestUtils.userDAO.add(DAOTestUtils.getTestUser());
        List<User> sorted = DAOTestUtils.userDAO.getSorted(userQueryBuilder().getQuery());
        assertFalse(sorted.isEmpty());
        assertEquals(ONE, sorted.size());
    }

    @Test
    void testGetAllSortedByName() throws DAOException {
        List<User> users = getRandomUsers();
        for (int i = 0; i < 5; i++) {
            DAOTestUtils.userDAO.add(users.get(i));
        }
        users.sort(Comparator.comparing(User::getName));
        List<User> sorted = DAOTestUtils.userDAO.getSorted(userQueryBuilder().setSortField(NAME_FIELD).getQuery());
        assertIterableEquals(users, sorted);
    }

    @Test
    void testGetAllSortedByNameDesc() throws DAOException {
        List<User> users = getRandomUsers();
        for (int i = 0; i < 5; i++) {
            DAOTestUtils.userDAO.add(users.get(i));
        }
        users.sort(Comparator.comparing(User::getName).reversed());
        String query = userQueryBuilder().setSortField(NAME_FIELD).setOrder(DESC).getQuery();
        List<User> sorted = DAOTestUtils.userDAO.getSorted(query);
        assertIterableEquals(users, sorted);
    }

    @Test
    void testGetAllSortedByNamePagination() throws DAOException {
        List<User> users = getRandomUsers();
        for (int i = 0; i < 5; i++) {
            DAOTestUtils.userDAO.add(users.get(i));
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
        List<User> sorted = DAOTestUtils.userDAO.getSorted(query);
        assertIterableEquals(users, sorted);
    }

    @Test
    void testGetAllSortedByNamePaginationOffsetThree() throws DAOException {
        List<User> users = getRandomUsers();
        for (int i = 0; i < 5; i++) {
            DAOTestUtils.userDAO.add(users.get(i));
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
        List<User> sorted = DAOTestUtils.userDAO.getSorted(query);
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
        List<User> sorted = DAOTestUtils.userDAO.getSorted(query);
        assertIterableEquals(speakers, sorted);
    }

    @Test
    void testGetNumberOfRecords() throws DAOException {
        List<User> speakers = getSpeakers();
        String query = userQueryBuilder()
                .setRoleFilter("3")
                .getRecordQuery();
        int numberOfRecords = DAOTestUtils.userDAO.getNumberOfRecords(query);
        assertEquals(speakers.size(), numberOfRecords);
    }


    private List<User> getSpeakers() throws DAOException {
        List<User> users = getRandomUsers();
        for (int i = 0; i < 5; i++) {
            DAOTestUtils.userDAO.add(users.get(i));
        }
        List<User> speakers = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            User speaker = users.get(i);
            speaker.setRoleId(THREE);
            speakers.add(speaker);
            DAOTestUtils.userDAO.setUserRole(speaker.getEmail(),Role.SPEAKER);
        }
        return speakers;
    }

    private List<User> getRandomUsers() {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            users.add(getRandomUser(i));
        }
        return users;
    }

    private User getRandomUser(int i) {
        User user = DAOTestUtils.getTestUser();
        user.setId(i + 1);
        user.setEmail(user.getEmail() + i);
        user.setName(user.getName() + new Random().nextInt(100));
        return user;
    }
}