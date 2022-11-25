package ua.java.conferences.services;

import ua.java.conferences.dao.DAOFactory;
import ua.java.conferences.services.implementation.*;

public final class ServiceFactory {

    private final UserService userService;
    private final ReportService reportService;
    private final EventService eventService;

    private ServiceFactory(String dbImplementation) {
        DAOFactory daoFactory = getDAOFactory(dbImplementation);
        userService = new UserServiceImpl(daoFactory.getUserDAO());
        reportService = new ReportServiceImpl(daoFactory.getReportDAO());
        eventService = new EventServiceImpl(daoFactory.getEventDAO());
    }

    public static ServiceFactory getInstance(String dbImplementation) {
        return new  ServiceFactory(dbImplementation);
    }

    private DAOFactory getDAOFactory(String dbImplementation) {
        return DAOFactory.getInstance(dbImplementation);
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