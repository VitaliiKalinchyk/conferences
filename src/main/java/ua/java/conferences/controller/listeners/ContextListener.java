package ua.java.conferences.controller.listeners;

import jakarta.servlet.*;
import org.slf4j.*;
import ua.java.conferences.controller.context.AppContext;

public class ContextListener implements ServletContextListener {
    private static final Logger logger = LoggerFactory.getLogger(ContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        AppContext.createAppContext(sce.getServletContext());
        logger.info("AppContext is set");
    }
}
