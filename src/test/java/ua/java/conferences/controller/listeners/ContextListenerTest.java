package ua.java.conferences.controller.listeners;

import jakarta.servlet.*;
import org.junit.jupiter.api.Test;
import ua.java.conferences.controller.context.AppContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ContextListenerTest {
    private static final ServletContextEvent sce = mock(ServletContextEvent.class);
    private static final ServletContext servletContext = mock(ServletContext.class);

    @Test
    void testContextInitialized() {
        when(sce.getServletContext()).thenReturn(servletContext);
        new ContextListener().contextInitialized(sce);
        AppContext appContext = AppContext.getAppContext();
        System.out.println(servletContext);
        assertEquals(servletContext, appContext.getServletContext());
    }
}