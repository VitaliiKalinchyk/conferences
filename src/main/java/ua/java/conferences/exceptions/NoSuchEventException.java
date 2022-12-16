package ua.java.conferences.exceptions;

import static ua.java.conferences.exceptions.constants.Message.NO_EVENT;

public class NoSuchEventException extends ServiceException {
    public NoSuchEventException() {
        super(NO_EVENT);
    }
    public NoSuchEventException(String message) {
        super(message);
    }
}