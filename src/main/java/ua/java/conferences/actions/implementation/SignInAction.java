package ua.java.conferences.actions.implementation;

import jakarta.servlet.http.HttpServletRequest;

import ua.java.conferences.actions.Action;
import ua.java.conferences.services.*;
import ua.java.conferences.entities.User;
import ua.java.conferences.entities.role.Role;
import ua.java.conferences.exceptions.ServiceException;


import static ua.java.conferences.actions.constants.ActionConstants.*;

public class SignInAction implements Action {

    private final UserService userService;

    public SignInAction() {
        userService = ServiceFactory.getInstance().getUserService();
    }
    @Override
    public String execute(HttpServletRequest request) {
        String path = "";
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        User user = null;
        Role role = null;
        try {
            user = userService.getByEmail(email);
            role = userService.getUsersRole(user.getId());
        } catch (ServiceException e) {
            path = "/404.jsp";
        }
        if (user == null || role == null) {
            path = "/401.jsp";
        } else if (!password.equals(user.getPassword()) ) {
            path = "sign-in.jsp";
        } else {
            setSessionAttributes(request, user, role);
            switch (role) {
                case VISITOR: path = "visitor/cabinet"; break;
                case SPEAKER: path = "speaker/cabinet"; break;
                case MODERATOR: path = "moderator/cabinet"; break;
                case ADMIN: path = "admin/cabinet"; break;
            }
        }
        return path;
    }

    private static void setSessionAttributes(HttpServletRequest request, User user, Role role) {
        request.getSession().setAttribute(USER, user);
        request.getSession().setAttribute(ROLE, role);
    }
}