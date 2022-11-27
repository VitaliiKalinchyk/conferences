package ua.java.conferences.exceptions;

public class IncorrectFormatException extends ServiceException {

    public IncorrectFormatException(String message) {
        super(message);
    }

    public static class Message {

        private Message() {}

        public static final String EMAIL = "Enter correct email";

        public static final String PASSWORD = "Enter correct password";

        public static final String NAME = "Enter correct name";

        public static final String SURNAME = "Enter correct surname";

        public static final String TOPIC = "Enter correct topic name";

        public static final String TITLE = "Enter correct title name";

        public static final String DATE = "Enter valid date";

        public static final String LOCATION = "Enter correct location";

        public static final String DESCRIPTION = "Enter correct description";
    }
}