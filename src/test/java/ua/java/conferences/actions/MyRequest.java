package ua.java.conferences.actions;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpSession;

import java.util.*;

public class MyRequest extends HttpServletRequestWrapper {
    private final Map<String, Object> attributes = new HashMap<>();
    private final HttpSession session = new MySession();
    private String encoding;

    public MyRequest(HttpServletRequest request) {
        super(request);
    }

    @Override
    public void setAttribute(String name, Object object) {
        attributes.put(name, object);
    }

    @Override
    public Object getAttribute(String name) {
        return attributes.get(name);
    }

    @Override
    public HttpSession getSession() {
        return session;
    }

    @Override
    public String getCharacterEncoding() {
        return encoding;
    }

    @Override
    public void setCharacterEncoding(String enc) {
        encoding = enc;
    }
}
