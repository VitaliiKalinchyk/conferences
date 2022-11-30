package ua.java.conferences.exceptions;

public class NoSuchReportException extends ServiceException {

    public NoSuchReportException() {
        super("error.report.absent");
    }
}