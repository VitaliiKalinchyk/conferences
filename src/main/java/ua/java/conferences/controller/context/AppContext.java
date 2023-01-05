package ua.java.conferences.controller.context;

import jakarta.servlet.ServletContext;
import lombok.*;
import org.slf4j.*;
import ua.java.conferences.model.connection.MyDataSource;
import ua.java.conferences.model.dao.DAOFactory;
import ua.java.conferences.model.services.*;
import ua.java.conferences.utils.*;

import javax.sql.DataSource;

import java.io.*;
import java.util.Properties;

import static ua.java.conferences.model.dao.constants.DbImplementations.MYSQL;

public class AppContext {
    private static final Logger logger = LoggerFactory.getLogger(AppContext.class);
    private static AppContext appContext;
    @Getter private final EventService eventService;
    @Getter private final UserService userService;
    @Getter private final ReportService reportService;
    @Getter private final EmailSender emailSender;
    @Getter private final PdfUtil pdfUtil = new PdfUtil();
    @Getter private final Captcha captcha;
    @Getter private final ServletContext servletContext;

    private AppContext(ServletContext servletContext, String propertiesFile) {
        this.servletContext = servletContext;
        Properties properties = getProperties(propertiesFile);
        emailSender = new EmailSender(properties);
        captcha = new Captcha(properties);
        DataSource dataSource = MyDataSource.getDataSource(properties);
        DAOFactory daoFactory = DAOFactory.getInstance(MYSQL, dataSource);
        ServiceFactory serviceFactory = ServiceFactory.getInstance(daoFactory);
        eventService =  serviceFactory.getEventService();
        userService = serviceFactory.getUserService();
        reportService = serviceFactory.getReportService();
    }

    public static AppContext getAppContext() {
        return appContext;
    }

    public static void createAppContext(ServletContext servletContext, String propertiesFile) {
        appContext = new AppContext(servletContext, propertiesFile);
    }

    private static Properties getProperties(String propertiesFile) {
        Properties properties = new Properties();
        try (InputStream resource = AppContext.class.getClassLoader().getResourceAsStream(propertiesFile)){
            properties.load(resource);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return properties;
    }
}