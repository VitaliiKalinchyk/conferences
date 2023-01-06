package ua.java.conferences.controller.actions;

import jakarta.servlet.ServletContext;
import org.junit.jupiter.api.Test;
import ua.java.conferences.controller.actions.implementation.base.*;
import ua.java.conferences.controller.context.AppContext;

import java.net.MalformedURLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static ua.java.conferences.controller.actions.ActionFactory.getActionFactory;
import static ua.java.conferences.controller.actions.constants.ActionNames.SIGN_IN_ACTION;
import static ua.java.conferences.controller.actions.util.Util.FONT;

class ActionFactoryTest {
    private static final String PROPERTIES_FILE = "context.properties";
    private final ServletContext servletContext = mock(ServletContext.class);

    @Test
    void testCreateAction() throws MalformedURLException {
        when(servletContext.getResource(FONT)).thenThrow(MalformedURLException.class);
        AppContext.createAppContext(servletContext, PROPERTIES_FILE);
        ActionFactory actionFactory = getActionFactory();
        Action action = actionFactory.createAction(SIGN_IN_ACTION);
        assertInstanceOf(SignInAction.class, action);
    }

    @Test
    void testDefaultAction() throws MalformedURLException {
        when(servletContext.getResource(FONT)).thenThrow(MalformedURLException.class);
        AppContext.createAppContext(servletContext, PROPERTIES_FILE);
        ActionFactory actionFactory = getActionFactory();
        Action action = actionFactory.createAction("wrongName");
        assertInstanceOf(DefaultAction.class, action);
    }
}