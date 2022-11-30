package ua.java.conferences.exceptions;

public class NoSuchEventException extends ServiceException {

    public NoSuchEventException() {
        super("error.event.absent");
    }
}