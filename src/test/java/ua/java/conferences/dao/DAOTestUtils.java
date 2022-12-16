package ua.java.conferences.dao;

import org.apache.ibatis.jdbc.ScriptRunner;
import ua.java.conferences.entities.*;

import java.io.*;
import java.sql.*;

import static ua.java.conferences.Constants.*;
import static ua.java.conferences.dao.constants.DbImplementations.MYSQL;

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
        return new User.Builder()
                .setId(ID_VALUE)
                .setEmail(EMAIL)
                .setName(NAME)
                .setSurname(SURNAME)
                .setPassword(PASSWORD)
                .setRoleId(ROLE_ID)
                .get();
    }

    public static Event getTestEvent() {
        return new Event.Builder()
                .setId(ID_VALUE)
                .setTitle(TITLE)
                .setDate(DATE)
                .setLocation(LOCATION)
                .setDescription(DESCRIPTION)
                .get();
    }

    public static Report getTestReport() {
        return (new Report.Builder())
                .setId(ID_VALUE)
                .setTopic(TOPIC)
                .setEvent(getTestEvent())
                .setSpeaker(getTestUser())
                .get();
    }
}