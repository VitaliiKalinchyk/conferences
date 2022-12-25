package ua.java.conferences.controller.actions.implementation.base;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ua.java.conferences.controller.actions.MyRequest;
import ua.java.conferences.controller.actions.MySession;
import ua.java.conferences.dto.UserDTO;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static ua.java.conferences.controller.actions.constants.Pages.SIGN_IN_PAGE;
import static ua.java.conferences.controller.actions.constants.Parameters.LOGGED_USER;

class SignOutActionTest {
    private final HttpServletRequest request = mock(HttpServletRequest.class);

    @Test
    void testExecute() {
        MyRequest myRequest = new MyRequest(request);
        myRequest.getSession().setAttribute(LOGGED_USER, UserDTO.builder().build());
        assertEquals(SIGN_IN_PAGE, new SignOutAction().execute(myRequest));
        Assertions.assertFalse(((MySession)myRequest.getSession()).isValid);
    }
}