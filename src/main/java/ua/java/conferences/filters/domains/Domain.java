package ua.java.conferences.filters.domains;

public abstract class Domain {
    protected String servletPath;
    protected String action;

    protected Domain(String servletPath, String action) {
        this.servletPath = servletPath;
        this.action = action;
    }

    public boolean checkAccess() {
        return !checkPages() || !checkActions();
    }

    protected abstract boolean checkPages();

    protected abstract boolean checkActions();
}