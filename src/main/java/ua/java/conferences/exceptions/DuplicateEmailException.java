package ua.java.conferences.exceptions;

public class DuplicateEmailException extends ServiceException {

    public DuplicateEmailException() {
        super("error.email.duplicate");
    }
}