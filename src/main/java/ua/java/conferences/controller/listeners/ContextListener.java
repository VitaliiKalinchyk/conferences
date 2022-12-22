package ua.java.conferences.controller.listeners;

import jakarta.servlet.*;
import org.slf4j.*;

public class ContextListener implements ServletContextListener {
    private static final Logger logger = LoggerFactory.getLogger(ContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("Servlet context is initialized");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("Servlet context is destroyed");
    }
}
