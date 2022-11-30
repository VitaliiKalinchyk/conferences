package ua.java.conferences.exceptions;

public class IncorrectFormatException extends ServiceException {

    public IncorrectFormatException(String message) {
        super(message);
    }

    public static class Message {

        private Message() {}

        public static final String ENTER_CORRECT_EMAIL = "error.email.format";

        public static final String ENTER_CORRECT_PASSWORD = "error.pass.format";

        public static final String ENTER_CORRECT_NAME = "error.name.format";

        public static final String ENTER_CORRECT_SURNAME = "error.surname.format";

        public static final String ENTER_CORRECT_TOPIC = "error.topic.format";

        public static final String ENTER_CORRECT_TITLE = "error.title.format";

        public static final String ENTER_VALID_DATE = "error.date.format";

        public static final String ENTER_CORRECT_LOCATION = "error.location.format=";

        public static final String ENTER_CORRECT_DESCRIPTION = "error.description.format";
    }
}