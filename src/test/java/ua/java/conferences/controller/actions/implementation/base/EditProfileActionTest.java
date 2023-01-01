package ua.java.conferences.controller.actions.implementation.base;

import jakarta.servlet.http.*;
import org.junit.jupiter.api.Test;
import ua.java.conferences.controller.actions.MyRequest;
import ua.java.conferences.controller.context.AppContext;
import ua.java.conferences.dto.UserDTO;
import ua.java.conferences.exceptions.DuplicateEmailException;
import ua.java.conferences.exceptions.ServiceException;
import ua.java.conferences.model.services.UserService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static ua.java.conferences.controller.actions.ActionUtil.getActionToRedirect;
import static ua.java.conferences.controller.actions.constants.ActionNames.EDIT_PROFILE_ACTION;
import static ua.java.conferences.controller.actions.constants.ParameterValues.SUCCEED_UPDATE;
import static ua.java.conferences.controller.actions.constants.Parameters.*;
import static ua.java.conferences.exceptions.constants.Message.DUPLICATE_EMAIL;

class EditProfileActionTest {
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final AppContext appContext = mock(AppContext.class);
    private final UserService userService = mock(UserService.class);

    @Test
    void testExecutePost() throws ServiceException {
        MyRequest myRequest = new MyRequest(request);
        UserDTO user = getUserDTO();
        myRequest.getSession().setAttribute(LOGGED_USER, user);
        setPostRequest();
        when(appContext.getUserService()).thenReturn(userService);
        doNothing().when(userService).update(isA(UserDTO.class));
        String path = new EditProfileAction(appContext).execute(myRequest, response);

        assertEquals(getActionToRedirect(EDIT_PROFILE_ACTION), path);
        assertEquals(SUCCEED_UPDATE, myRequest.getSession().getAttribute(MESSAGE));
        assertEquals("newEmail", user.getEmail());
        assertEquals("newName", user.getName());
        assertEquals("newSurname", user.getSurname());

    }

    @Test
    void testExecuteBadPost() throws ServiceException {
        MyRequest myRequest = new MyRequest(request);
        UserDTO user = getUserDTO();
        myRequest.getSession().setAttribute(LOGGED_USER, user);
        setPostRequest();
        when(appContext.getUserService()).thenReturn(userService);
        doThrow(new DuplicateEmailException()).when(userService).update(isA(UserDTO.class));
        String path = new EditProfileAction(appContext).execute(myRequest, response);

        assertEquals(getActionToRedirect(EDIT_PROFILE_ACTION), path);
        assertEquals(DUPLICATE_EMAIL, myRequest.getSession().getAttribute(ERROR));
    }

    private static UserDTO getUserDTO() {
        return UserDTO.builder().id(1).email("email").name("name").surname("surname").build();
    }

    void setPostRequest() {
        when(request.getMethod()).thenReturn("POST");
        when(request.getParameter(EMAIL)).thenReturn("newEmail");
        when(request.getParameter(NAME)).thenReturn("newName");
        when(request.getParameter(SURNAME)).thenReturn("newSurname");
    }
}