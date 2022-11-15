package ua.java.conferences.dao.mysql;

import ua.java.conferences.dao.*;

public class MysqlDAOFactory extends DAOFactory {
    private EventDAO eventDAO;
    private ReportDAO reportDAO;
    private UserDAO userDAO;

    public EventDAO getEventDAO() {
        if (this.eventDAO == null) {
            this.eventDAO = new MysqlEventDAO();
        }

        return this.eventDAO;
    }

    public ReportDAO getReportDAO() {
        if (this.reportDAO == null) {
            this.reportDAO = new MysqlReportDAO();
        }

        return this.reportDAO;
    }

    public UserDAO getUserDAO() {
        if (this.userDAO == null) {
            this.userDAO = new MysqlUserDAO();
        }

        return this.userDAO;
    }
}