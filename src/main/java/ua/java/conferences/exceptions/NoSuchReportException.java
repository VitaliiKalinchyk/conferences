package ua.java.conferences.exceptions;

import static ua.java.conferences.exceptions.constants.Message.NO_REPORT;

public class NoSuchReportException extends ServiceException {
    public NoSuchReportException() {
        super(NO_REPORT);
    }
}