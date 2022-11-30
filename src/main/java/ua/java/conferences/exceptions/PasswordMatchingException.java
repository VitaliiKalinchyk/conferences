package ua.java.conferences.exceptions;

public class PasswordMatchingException extends ServiceException{

    public PasswordMatchingException() {
        super("error.pass.match");
    }
}