package ua.java.conferences.actions;

import jakarta.servlet.http.HttpServletRequest;
import ua.java.conferences.dto.request.UserRequestDTO;

import static ua.java.conferences.actions.constants.ActionConstants.*;

public interface ActionPost {

    String executePost(HttpServletRequest request);

    default String getPath(HttpServletRequest request, String path){
        String postPath = (String) request.getSession().getAttribute(CURRENT_PATH);
        if (postPath != null) {
            path = postPath;
        }
        return path;
    }

    default void transferUserRequestDTOFromSessionToRequest(HttpServletRequest request) {
        UserRequestDTO user = (UserRequestDTO) request.getSession().getAttribute(USER);
        if (user != null) {
            request.setAttribute(USER, user);
            request.getSession().removeAttribute(USER);
        }
    }

    default void transferStringFromSessionToRequest(HttpServletRequest request, String attributeName) {
        String attributeValue = (String) request.getSession().getAttribute(attributeName);
        if (attributeValue != null) {
            request.setAttribute(attributeName, attributeValue);
            request.getSession().removeAttribute(attributeName);
        }
    }
}