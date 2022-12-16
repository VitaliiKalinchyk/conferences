package ua.java.conferences.actions;

import jakarta.servlet.http.HttpServletRequest;
import ua.java.conferences.dto.EventDTO;
import ua.java.conferences.dto.UserDTO;

import java.util.StringJoiner;

import static ua.java.conferences.actions.constants.Pages.*;
import static ua.java.conferences.actions.constants.Parameters.*;
import static ua.java.conferences.dao.constants.DbImplementations.*;

public class ActionUtil {

    public static final String DB_IMPLEMENTATION = MYSQL;

    public static boolean isPostMethod(HttpServletRequest request) {
        return request.getMethod().equals("POST");
    }

    public static String getPath(HttpServletRequest request){
        return (String) request.getSession().getAttribute(CURRENT_PATH);
    }

    public static void transferUserDTOFromSessionToRequest(HttpServletRequest request) {
        UserDTO user = (UserDTO) request.getSession().getAttribute(USER);
        if (user != null) {
            request.setAttribute(USER, user);
            request.getSession().removeAttribute(USER);
        }
    }

    public static void transferEventDTOFromSessionToRequest(HttpServletRequest request, String attributeName) {
        EventDTO event = (EventDTO) request.getSession().getAttribute(EVENT);
        if (event != null) {
            request.setAttribute(attributeName, event);
            request.getSession().removeAttribute(attributeName);
        }
    }

    public static void transferStringFromSessionToRequest(HttpServletRequest request, String attributeName) {
        String attributeValue = (String) request.getSession().getAttribute(attributeName);
        if (attributeValue != null) {
            request.setAttribute(attributeName, attributeValue);
            request.getSession().removeAttribute(attributeName);
        }
    }

    public static String getActionToRedirect(String action, String... parameters) {
        String base = CONTROLLER_PAGE + "?" + ACTION + "=" +action;
        StringJoiner stringJoiner = new StringJoiner("&", "&", "");
        for (int i = 0; i < parameters.length; i+=2) {
            stringJoiner.add(parameters[i] + "=" + parameters[i + 1]);
        }
        return base + (parameters.length > 0 ? stringJoiner : "");
    }

    private ActionUtil() {}
}