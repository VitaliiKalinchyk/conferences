package ua.java.conferences.actions.implementation;

import jakarta.servlet.http.HttpServletRequest;
import ua.java.conferences.actions.Action;

public class DefaultAction implements Action {
    @Override
    public String execute(HttpServletRequest request) {
        return "/index.html";
    }
}
