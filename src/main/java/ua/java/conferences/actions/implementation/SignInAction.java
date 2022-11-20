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
        User user;
        Role role;
        try {
            user = userService.getByEmail(email);
            role = userService.getUsersRole(user.getId());
            if (!password.equals(user.getPassword()) ) {
                request.getServletContext().setAttribute("error", "Wrong Password");
                path = "sign-in.jsp";
            } else {
                setSessionAttributes(request, user, role);
                switch (role) {
                    case VISITOR: path = "visitor/profile"; break;
                    case SPEAKER: path = "speaker/profile"; break;
                    case MODERATOR: path = "moderator/profile"; break;
                    case ADMIN: path = "admin/profile"; break;
                }
            }
        } catch (ServiceException | NullPointerException e) {
            request.getServletContext().setAttribute("error", "Wrong Email");
            path = "sign-in.jsp";
        }
        return path;
    }

    private static void setSessionAttributes(HttpServletRequest request, User user, Role role) {
        request.getSession().setAttribute(USER, user);
        request.getSession().setAttribute(ROLE, role);
    }
}