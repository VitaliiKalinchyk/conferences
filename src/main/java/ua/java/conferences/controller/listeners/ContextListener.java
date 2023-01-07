package ua.java.conferences.controller.listeners;

import jakarta.servlet.*;
import org.slf4j.*;
import ua.java.conferences.controller.context.AppContext;

/**
 * ContextListener  class.
 *
 * @author Vitalii Kalinchyk
 * @version 1.0
 */
public class ContextListener implements ServletContextListener {
    private static final Logger logger = LoggerFactory.getLogger(ContextListener.class);

    /** Name of properties file to configure DataSource, EmailSender and Captcha */
    private static final String PROPERTIES_FILE = "context.properties";

    /**
     * creates AppContext and passes ServletContext and properties to initialize all required classes
     * @param sce passed by application
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        AppContext.createAppContext(sce.getServletContext(), PROPERTIES_FILE);
        logger.info("AppContext is set");
    }
}
