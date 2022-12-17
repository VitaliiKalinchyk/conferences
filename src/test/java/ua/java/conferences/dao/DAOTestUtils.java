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
        return User.builder()
                .id(ID_VALUE)
                .email(EMAIL)
                .name(NAME)
                .surname(SURNAME)
                .password(PASSWORD)
                .roleId(ROLE_ID)
                .build();
    }

    public static Event getTestEvent() {
        return Event.builder()
                .id(ID_VALUE)
                .title(TITLE)
                .date(DATE)
                .location(LOCATION)
                .description(DESCRIPTION)
                .build();
    }

    public static Report getTestReport() {
        return Report.builder()
                .id(ID_VALUE)
                .topic(TOPIC)
                .event(getTestEvent())
                .speaker(getTestUser())
                .build();
    }
}