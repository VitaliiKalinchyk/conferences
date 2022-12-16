package ua.java.conferences.exceptions;

import static ua.java.conferences.exceptions.constants.Message.DUPLICATE_TITLE;

public class DuplicateTitleException extends ServiceException {
    public DuplicateTitleException() {
        super(DUPLICATE_TITLE);
    }
}