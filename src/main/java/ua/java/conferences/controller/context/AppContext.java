package ua.java.conferences.controller.context;

import lombok.*;
import ua.java.conferences.model.services.*;

import static ua.java.conferences.model.dao.constants.DbImplementations.MYSQL;

public class AppContext {
    private final ServiceFactory serviceFactory = ServiceFactory.getInstance(MYSQL);
    @Getter private final EventService eventService =  serviceFactory.getEventService();
    @Getter private final UserService userService = serviceFactory.getUserService();
    @Getter private final ReportService reportService = serviceFactory.getReportService();

    public static AppContext getAppContext() {
        return new AppContext();
    }

    private AppContext() {}
}