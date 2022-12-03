package ua.java.conferences.services;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static ua.java.conferences.dao.constants.DbImplementations.MYSQL;

class ServiceFactoryTest {

    @Test
    void testGetInstance() {
        assertDoesNotThrow(() -> ServiceFactory.getInstance(MYSQL));
        ServiceFactory factory = ServiceFactory.getInstance(MYSQL);
        assertDoesNotThrow(factory::getUserService);
        assertDoesNotThrow(factory::getReportService);
        assertDoesNotThrow(factory::getEventService);
    }
}