package ua.java.conferences.model.dao;

import javax.sql.DataSource;
import ua.java.conferences.model.dao.mysql.MysqlDAOFactory;
import ua.java.conferences.model.dao.constants.DbImplementations;

public abstract class DAOFactory {
    private static DAOFactory instance;

    protected DAOFactory() {}

    public static synchronized DAOFactory getInstance(String dbImplementation, DataSource dataSource) {
        if (instance == null && DbImplementations.MYSQL.equals(dbImplementation)) {
            instance = new MysqlDAOFactory(dataSource);
        }

        return instance;
    }

    public abstract EventDAO getEventDAO();

    public abstract ReportDAO getReportDAO();

    public abstract UserDAO getUserDAO();
}