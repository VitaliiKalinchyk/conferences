package ua.java.conferences.exceptions;

public class IncorrectFormatException extends ServiceException {

    public IncorrectFormatException(String message) {
        super(message);
    }

    public static class Message {

        private Message() {}

        public static final String ENTER_CORRECT_EMAIL = "Enter correct email";

        public static final String ENTER_CORRECT_PASSWORD = "Enter correct password";

        public static final String ENTER_CORRECT_NAME = "Enter correct name";

        public static final String ENTER_CORRECT_SURNAME = "Enter correct surname";

        public static final String ENTER_CORRECT_TOPIC = "Enter correct topic name";

        public static final String ENTER_CORRECT_TITLE = "Enter correct title name";

        public static final String ENTER_VALID_DATE = "Enter valid date";

        public static final String ENTER_CORRECT_LOCATION = "Enter correct location";

        public static final String ENTER_CORRECT_DESCRIPTION = "Enter correct description";
    }
}