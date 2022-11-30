package ua.java.conferences.exceptions;

public class IncorrectEmailException extends ServiceException{

    public IncorrectEmailException() {
        super("error.email.duplicate");
    }
}