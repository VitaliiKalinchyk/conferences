package ua.java.conferences.actions.implementation;

import jakarta.servlet.http.HttpServletRequest;
import ua.java.conferences.actions.Action;

public class IndexAction implements Action {
    @Override
    public String execute(HttpServletRequest request) {
        return "/index.html";
    }
}
