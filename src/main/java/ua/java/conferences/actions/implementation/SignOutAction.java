package ua.java.conferences.actions.implementation;

import ua.java.conferences.actions.Action;

import jakarta.servlet.http.*;

public class SignOutAction implements Action {
    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
        return "index.html";
    }
}