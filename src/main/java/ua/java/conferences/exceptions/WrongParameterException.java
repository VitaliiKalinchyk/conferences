package ua.java.conferences.exceptions;

import static ua.java.conferences.exceptions.constants.Message.INCORRECT_PARAMETER;

public class WrongParameterException extends ServiceException {
    public WrongParameterException() {
        super(INCORRECT_PARAMETER);
    }
}