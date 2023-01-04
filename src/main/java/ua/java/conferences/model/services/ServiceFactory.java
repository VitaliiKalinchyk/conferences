package ua.java.conferences.model.services;

import ua.java.conferences.model.dao.DAOFactory;
import ua.java.conferences.model.services.implementation.*;

public final class ServiceFactory {

    private final UserService userService;
    private final ReportService reportService;
    private final EventService eventService;

    private ServiceFactory(DAOFactory daoFactory) {
        userService = new UserServiceImpl(daoFactory.getUserDAO());
        reportService = new ReportServiceImpl(daoFactory.getReportDAO());
        eventService = new EventServiceImpl(daoFactory.getEventDAO());
    }

    public static ServiceFactory getInstance(DAOFactory daoFactory) {
        return new  ServiceFactory(daoFactory);
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