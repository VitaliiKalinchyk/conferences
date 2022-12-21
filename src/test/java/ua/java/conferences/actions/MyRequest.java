package ua.java.conferences.actions;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import java.util.HashMap;
import java.util.Map;

public class MyRequest extends HttpServletRequestWrapper {
    private final Map<String, Object> attributes = new HashMap<>();

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
}
