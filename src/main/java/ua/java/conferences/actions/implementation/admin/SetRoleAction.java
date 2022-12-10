package ua.java.conferences.actions.implementation.admin;

import jakarta.servlet.http.HttpServletRequest;
import ua.java.conferences.actions.Action;
import ua.java.conferences.entities.role.Role;
import ua.java.conferences.exceptions.ServiceException;
import ua.java.conferences.services.*;

import static ua.java.conferences.actions.ActionUtil.*;
import static ua.java.conferences.actions.constants.Pages.*;
import static ua.java.conferences.actions.constants.Parameters.*;

public class SetRoleAction implements Action {

    private final UserService userService;

    public SetRoleAction() {
        userService = ServiceFactory.getInstance(DB_IMPLEMENTATION).getUserService();
    }

    @Override
    public String execute(HttpServletRequest request) throws ServiceException {
        String userId = request.getParameter(USER_ID);
        int roleId = Role.valueOf(request.getParameter(ROLE)).getValue();
        userService.setRole(userId, roleId);
        request.setAttribute(USER, userService.view(userId));
        return USER_BY_EMAIL_PAGE;
    }
}
