package ua.java.conferences.exception;

public class DBException extends Exception {

    public DBException(String message) {
        super(message);
    }

    public DBException(Throwable cause) {
        super(cause);
    }
}