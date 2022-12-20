package ua.java.conferences.actions.implementation.admin;

import jakarta.servlet.http.HttpServletRequest;
import ua.java.conferences.actions.Action;
import ua.java.conferences.entities.role.Role;
import ua.java.conferences.exceptions.ServiceException;
import ua.java.conferences.services.*;

import static ua.java.conferences.actions.ActionUtil.*;
import static ua.java.conferences.actions.constants.ActionNames.*;
import static ua.java.conferences.actions.constants.Parameters.*;

public class SetRoleAction implements Action {
    private final UserService userService;

    public SetRoleAction() {
        userService = ServiceFactory.getInstance(DB_IMPLEMENTATION).getUserService();
    }

    @Override
    public String execute(HttpServletRequest request) throws ServiceException {
        String email = request.getParameter(EMAIL);
        int roleId = Role.valueOf(request.getParameter(ROLE)).getValue();
        userService.setRole(email, roleId);
        request.setAttribute(USER, userService.getByEmail(email));
        return getActionToRedirect(SEARCH_USER_ACTION, EMAIL, email);
    }
}
