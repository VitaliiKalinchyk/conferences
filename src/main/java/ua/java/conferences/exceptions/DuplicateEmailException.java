package ua.java.conferences.exceptions;

import static ua.java.conferences.exceptions.constants.Message.DUPLICATE_EMAIL;

public class DuplicateEmailException extends ServiceException {
    public DuplicateEmailException() {
        super(DUPLICATE_EMAIL);
    }
}