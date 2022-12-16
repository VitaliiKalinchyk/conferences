package ua.java.conferences.dao.mysql;

import ua.java.conferences.dao.*;

public class MysqlDAOFactory extends DAOFactory {
    private EventDAO eventDAO;
    private ReportDAO reportDAO;
    private UserDAO userDAO;

    public EventDAO getEventDAO() {
        if (eventDAO == null) {
            eventDAO = new MysqlEventDAO();
        }
        return eventDAO;
    }

    public ReportDAO getReportDAO() {
        if (reportDAO == null) {
            reportDAO = new MysqlReportDAO();
        }
        return reportDAO;
    }

    public UserDAO getUserDAO() {
        if (userDAO == null) {
            userDAO = new MysqlUserDAO();
        }
        return userDAO;
    }
}