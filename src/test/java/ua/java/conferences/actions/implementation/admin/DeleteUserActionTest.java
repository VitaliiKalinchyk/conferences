package ua.java.conferences.actions.implementation.admin;

import jakarta.servlet.http.*;
import org.junit.jupiter.api.Test;
import ua.java.conferences.actions.MyRequest;
import ua.java.conferences.exceptions.*;
import ua.java.conferences.services.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static ua.java.conferences.actions.ActionUtil.*;
import static ua.java.conferences.actions.constants.ActionNames.*;
import static ua.java.conferences.actions.constants.Pages.*;
import static ua.java.conferences.actions.constants.ParameterValues.*;
import static ua.java.conferences.actions.constants.Parameters.*;

class DeleteUserActionTest {
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpSession session = mock(HttpSession.class);
    private final UserService userService = mock(UserService.class);

    @Test
    void testExecutePost() throws ServiceException {
        when(request.getSession()).thenReturn(session);
        doNothing().when(session).setAttribute(MESSAGE, SUCCEED_DELETE);
        when(request.getMethod()).thenReturn("POST");
        when(request.getParameter(USER_ID)).thenReturn("1");
        doNothing().when(userService).delete("1");

        assertEquals(getActionToRedirect(DELETE_USER_ACTION), new DeleteUserAction().execute(request));
    }

    @Test
    void testExecutePostNull() throws ServiceException {
        when(request.getSession()).thenReturn(session);
        doNothing().when(session).setAttribute(MESSAGE, SUCCEED_DELETE);
        when(request.getMethod()).thenReturn("POST");
        when(request.getParameter(USER_ID)).thenReturn(null);
        doThrow(NoSuchUserException.class).when(userService).delete(null);

        assertThrows(NoSuchUserException.class, () -> new DeleteUserAction().execute(request));
    }


    @Test
    void testExecuteGet() throws ServiceException {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(MESSAGE)).thenReturn(SUCCEED_DELETE);
        when(request.getMethod()).thenReturn("GET");
        MyRequest myRequest = new MyRequest(request);

        assertEquals(SEARCH_USERS_PAGE, new DeleteUserAction().execute(myRequest));
        assertEquals(SUCCEED_DELETE, myRequest.getAttribute(MESSAGE));
    }
}