package ua.java.conferences.exceptions;

public class IncorrectPasswordException extends ServiceException{

    public IncorrectPasswordException() {
        super("error.pass.wrong");
    }
}