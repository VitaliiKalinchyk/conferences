package ua.java.conferences.dao.mysql;

import org.apache.ibatis.jdbc.ScriptRunner;
import ua.java.conferences.dao.*;
import ua.java.conferences.entity.*;
import ua.java.conferences.entity.builder.*;

import java.io.*;
import java.sql.*;
import java.time.LocalDate;

import static ua.java.conferences.connection.ConnectionConstants.MYSQL;

public final class DAOTestUtils {

    private static final String EMPTY_DB_URL = "jdbc:mysql://localhost/empty_conferences?user=root&password=";

    private static final String EMPTY_DB = "sql/empty_conferences.sql";

    private static final DAOFactory daoFactory = DAOFactory.getInstance(MYSQL);

    public static final UserDAO userDAO;

    public static final ReportDAO reportDAO;

    public static final EventDAO eventDAO;

    static {
        userDAO = daoFactory.getUserDAO();
        reportDAO = daoFactory.getReportDAO();
        eventDAO = daoFactory.getEventDAO();
    }

    public static void createEmptyDB() throws SQLException, FileNotFoundException {
        Connection con = DriverManager.getConnection(EMPTY_DB_URL);
        ScriptRunner sr = new ScriptRunner(con);
        Reader reader = new BufferedReader(new FileReader(EMPTY_DB));
        sr.setLogWriter(null);
        sr.runScript(reader);
    }

    public static User getTestUser() {
        return new UserBuilder()
                .setEmail("test@test.test")
                .setName("Test")
                .setSurname("Tester")
                .setPassword("test_password")
                .get();
    }

    public static Event getTestEvent() {
        return new EventBuilder()
                .setTitle("Event")
                .setDate(LocalDate.of(2022, 12, 12))
                .setLocation("Kyiv")
                .setDescription("test event")
                .get();
    }

    public static Report getTestReport() {
        return (new ReportBuilder())
                .setTopic("Report")
                .get();
    }
}