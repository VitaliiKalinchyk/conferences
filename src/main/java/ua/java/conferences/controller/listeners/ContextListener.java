package ua.java.conferences.controller.listeners;

import jakarta.servlet.*;
import org.slf4j.*;
import ua.java.conferences.controller.context.AppContext;

public class ContextListener implements ServletContextListener {
    private static final Logger logger = LoggerFactory.getLogger(ContextListener.class);
    private static final String PROPERTIES_FILE = "context.properties";

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        AppContext.createAppContext(sce.getServletContext(), PROPERTIES_FILE);
        logger.info("AppContext is set");
    }
}
