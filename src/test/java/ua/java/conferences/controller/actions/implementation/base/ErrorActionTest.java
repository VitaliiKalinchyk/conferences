package ua.java.conferences.controller.actions.implementation.base;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static ua.java.conferences.controller.actions.constants.Pages.ERROR_PAGE;

class ErrorActionTest {
    private final HttpServletRequest request = mock(HttpServletRequest.class);

    @Test
    void testExecute() {
        assertEquals(ERROR_PAGE, new ErrorAction().execute(request));
    }
}