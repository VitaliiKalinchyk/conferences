package ua.java.conferences.exceptions;

import static ua.java.conferences.exceptions.constants.Message.WRONG_PASSWORD;

public class IncorrectPasswordException extends ServiceException{
    public IncorrectPasswordException() {
        super(WRONG_PASSWORD);
    }
}