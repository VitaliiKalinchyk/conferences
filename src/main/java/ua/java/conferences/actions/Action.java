package ua.java.conferences.actions;

import jakarta.servlet.http.HttpServletRequest;

public interface Action {

    String executeGet(HttpServletRequest request);
}