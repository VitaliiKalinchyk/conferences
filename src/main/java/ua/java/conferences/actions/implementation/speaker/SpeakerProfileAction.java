package ua.java.conferences.actions.implementation.speaker;

import jakarta.servlet.http.HttpServletRequest;
import ua.java.conferences.actions.Action;

public class SpeakerProfileAction implements Action {
    @Override
    public String execute(HttpServletRequest request) {
        return "/speaker/profile.jsp";
    }
}