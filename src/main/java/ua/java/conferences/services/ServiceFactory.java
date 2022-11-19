package ua.java.conferences.services;

import ua.java.conferences.dao.DAOFactory;
import ua.java.conferences.services.implementation.EventServiceImpl;
import ua.java.conferences.services.implementation.ReportServiceImpl;
import ua.java.conferences.services.implementation.UserServiceImpl;

import static ua.java.conferences.connection.ConnectionConstants.MYSQL;

public final class ServiceFactory {
    private static final ServiceFactory SERVICE_FACTORY = new ServiceFactory();

    private final UserService userService;
    private final ReportService reportService;
    private final EventService eventService;

    private ServiceFactory() {
        userService = new UserServiceImpl(DAOFactory.getInstance(MYSQL).getUserDAO());
        reportService = new ReportServiceImpl(DAOFactory.getInstance(MYSQL).getReportDAO());
        eventService = new EventServiceImpl(DAOFactory.getInstance(MYSQL).getEventDAO());
    }

    public static ServiceFactory getInstance() {
        return SERVICE_FACTORY;
    }

    public UserService getUserService() {
        return userService;
    }

    public ReportService getReportService() {
        return reportService;
    }

    public EventService getEventService() {
        return eventService;
    }
}