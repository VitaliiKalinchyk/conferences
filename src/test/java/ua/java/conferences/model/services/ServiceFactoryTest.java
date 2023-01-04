package ua.java.conferences.model.services;

import org.junit.jupiter.api.Test;
import ua.java.conferences.model.connection.MyDataSource;
import ua.java.conferences.model.dao.DAOFactory;

import static org.junit.jupiter.api.Assertions.*;
import static ua.java.conferences.model.dao.constants.DbImplementations.MYSQL;

class ServiceFactoryTest {

    @Test
    void testGetInstance() {
        DAOFactory daoFactory = DAOFactory.getInstance(MYSQL, MyDataSource.getDataSource());
        assertDoesNotThrow(() -> ServiceFactory.getInstance(daoFactory));
        ServiceFactory factory = ServiceFactory.getInstance(daoFactory);
        assertDoesNotThrow(factory::getUserService);
        assertDoesNotThrow(factory::getReportService);
        assertDoesNotThrow(factory::getEventService);
    }
}