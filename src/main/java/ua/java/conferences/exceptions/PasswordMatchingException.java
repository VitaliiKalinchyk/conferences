package ua.java.conferences.exceptions;

import static ua.java.conferences.exceptions.constants.Message.PASSWORD_MATCHING;

public class PasswordMatchingException extends ServiceException{
    public PasswordMatchingException() {
        super(PASSWORD_MATCHING);
    }
}