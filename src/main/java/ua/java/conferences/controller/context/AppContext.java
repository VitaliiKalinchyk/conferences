package ua.java.conferences.controller.context;

import jakarta.servlet.ServletContext;
import lombok.*;
import ua.java.conferences.model.services.*;
import ua.java.conferences.utils.Captcha;
import ua.java.conferences.utils.EmailSender;
import ua.java.conferences.utils.PdfUtil;

import static ua.java.conferences.model.dao.constants.DbImplementations.MYSQL;

public class AppContext {
    private static AppContext appContext;
    private final ServiceFactory serviceFactory = ServiceFactory.getInstance(MYSQL);
    @Getter private final EventService eventService =  serviceFactory.getEventService();
    @Getter private final UserService userService = serviceFactory.getUserService();
    @Getter private final ReportService reportService = serviceFactory.getReportService();
    @Getter private final EmailSender emailSender = new EmailSender();
    @Getter private final PdfUtil pdfUtil = new PdfUtil();
    @Getter private final Captcha captcha = new Captcha();
    @Getter private final ServletContext servletContext;

    public static AppContext getAppContext() {
        return appContext;
    }

    public static void createAppContext(ServletContext servletContext) {
        appContext = new AppContext(servletContext);
    }

    private AppContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }
}