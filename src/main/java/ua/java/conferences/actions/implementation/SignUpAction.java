package ua.java.conferences.actions.implementation;

import jakarta.servlet.http.HttpServletRequest;
import ua.java.conferences.actions.Action;
import ua.java.conferences.entities.User;
import ua.java.conferences.exceptions.ServiceException;
import ua.java.conferences.services.*;

public class SignUpAction implements Action {
    private final UserService userService;

    public SignUpAction() {
        userService = ServiceFactory.getInstance().getUserService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        String path = "sign-in.jsp";
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        boolean notification = Boolean.parseBoolean(request.getParameter("notification"));
        User user = new User.UserBuilder()
                .setEmail(email)
                .setPassword(password)
                .setName(name)
                .setSurname(surname)
                .setEmailNotification(notification)
                .get();
        try {
            userService.add(user);
        } catch (ServiceException e) {
            path = "401.jsp";
        }
        return path;
    }
}