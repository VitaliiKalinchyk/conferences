package ua.java.conferences.filters.domains;

public interface Domain {

    default boolean checkAccess() {
        return !checkPages() || !checkActions();
    }

    boolean checkPages();

    boolean checkActions();
}