package ua.java.conferences.dao;

import ua.java.conferences.dao.mysql.MysqlDAOFactory;

import static ua.java.conferences.dao.constants.DbImplementations.MYSQL;

public abstract class DAOFactory {

    private static DAOFactory instance;

    protected DAOFactory() {}

    public static synchronized DAOFactory getInstance(String dbImplementation) {
        if (instance == null && MYSQL.equals(dbImplementation)) {
            instance = new MysqlDAOFactory();
        }

        return instance;
    }

    public abstract EventDAO getEventDAO();

    public abstract ReportDAO getReportDAO();

    public abstract UserDAO getUserDAO();
}