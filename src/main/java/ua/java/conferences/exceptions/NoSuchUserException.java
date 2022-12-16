package ua.java.conferences.exceptions;

import static ua.java.conferences.exceptions.constants.Message.NO_USER;

public class NoSuchUserException extends ServiceException {
    public NoSuchUserException() {
        super(NO_USER);
    }
}