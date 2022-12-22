package ua.java.conferences.actions.implementation.admin;

import jakarta.servlet.http.*;
import org.junit.jupiter.api.Test;
import ua.java.conferences.actions.MyRequest;
import ua.java.conferences.controller.context.AppContext;
import ua.java.conferences.controller.actions.implementation.admin.DeleteUserAction;
import ua.java.conferences.exceptions.*;
import ua.java.conferences.model.services.UserService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static ua.java.conferences.controller.actions.ActionUtil.*;
import static ua.java.conferences.controller.actions.constants.ActionNames.DELETE_USER_ACTION;
import static ua.java.conferences.controller.actions.constants.Pages.SEARCH_USERS_PAGE;
import static ua.java.conferences.controller.actions.constants.ParameterValues.SUCCEED_DELETE;
import static ua.java.conferences.controller.actions.constants.Parameters.*;

class DeleteUserActionTest {
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final AppContext appContext = mock(AppContext.class);
    private final UserService userService = mock(UserService.class);

    @Test
    void testExecutePost() throws ServiceException {
        when(request.getMethod()).thenReturn("POST");
        when(request.getParameter(USER_ID)).thenReturn("1");
        when(appContext.getUserService()).thenReturn(userService);
        MyRequest myRequest = new MyRequest(request);
        doNothing().when(userService).delete("1");
        assertEquals(getActionToRedirect(DELETE_USER_ACTION), new DeleteUserAction(appContext).execute(myRequest));
    }

    @Test
    void testExecutePostNull() throws ServiceException {
        when(request.getMethod()).thenReturn("POST");
        when(request.getParameter(USER_ID)).thenReturn(null);
        when(appContext.getUserService()).thenReturn(userService);
        MyRequest myRequest = new MyRequest(request);
        doThrow(NoSuchUserException.class).when(userService).delete(null);

        assertThrows(NoSuchUserException.class, () -> new DeleteUserAction(appContext).execute(myRequest));
    }


    @Test
    void testExecuteGet() throws ServiceException {
        when(request.getMethod()).thenReturn("GET");
        MyRequest myRequest = new MyRequest(request);
        myRequest.getSession().setAttribute(MESSAGE, SUCCEED_DELETE);
        assertEquals(SEARCH_USERS_PAGE, new DeleteUserAction(appContext).execute(myRequest));
        assertEquals(SUCCEED_DELETE, myRequest.getAttribute(MESSAGE));
    }
}