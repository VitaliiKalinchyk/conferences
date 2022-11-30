package ua.java.conferences.exceptions;

public class NoSuchUserException extends ServiceException {

    public NoSuchUserException() {
        super("error.email.absent");
    }
}