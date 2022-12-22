package ua.java.conferences.actions.implementation.base;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import ua.java.conferences.controller.actions.implementation.base.DefaultAction;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static ua.java.conferences.controller.actions.constants.Pages.INDEX_PAGE;

class DefaultActionTest {
    private final HttpServletRequest request = mock(HttpServletRequest.class);

    @Test
    void testExecute() {
        assertEquals(INDEX_PAGE, new DefaultAction().execute(request));
    }
}