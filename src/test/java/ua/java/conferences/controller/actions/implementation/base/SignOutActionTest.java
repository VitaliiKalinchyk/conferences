package ua.java.conferences.controller.actions.implementation.base;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ua.java.conferences.controller.actions.MyRequest;
import ua.java.conferences.controller.actions.MySession;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static ua.java.conferences.controller.actions.constants.Pages.SIGN_IN_PAGE;

class SignOutActionTest {
    private final HttpServletRequest request = mock(HttpServletRequest.class);

    @Test
    void testExecute() {
        MyRequest myRequest = new MyRequest(request);
        assertEquals(SIGN_IN_PAGE, new SignOutAction().execute(myRequest));
        Assertions.assertFalse(((MySession)myRequest.getSession()).isValid);
    }
}