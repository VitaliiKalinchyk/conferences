package ua.java.conferences.model.dao.mysql;

import javax.sql.DataSource;
import ua.java.conferences.model.dao.*;

public class MysqlDAOFactory extends DAOFactory {
    private EventDAO eventDAO;
    private ReportDAO reportDAO;
    private UserDAO userDAO;
    private final DataSource dataSource;

    public MysqlDAOFactory(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public EventDAO getEventDAO() {
        if (eventDAO == null) {
            eventDAO = new MysqlEventDAO(dataSource);
        }
        return eventDAO;
    }

    public ReportDAO getReportDAO() {
        if (reportDAO == null) {
            reportDAO = new MysqlReportDAO(dataSource);
        }
        return reportDAO;
    }

    public UserDAO getUserDAO() {
        if (userDAO == null) {
            userDAO = new MysqlUserDAO(dataSource);
        }
        return userDAO;
    }
}