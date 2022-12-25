package ua.java.conferences.utils.constants;

public class Email {
    private static final String URI = "http://localhost:8080/conferences/";
    public static final String SUBJECT_GREETINGS = "Welcome to Conference Smart App!";
    public static final String SUBJECT_NOTIFICATION = "Conference Smart App notification!";
    public static final String HELLO = "Hello %s,<br>";
    public static final String DOUBLE_ENTER = "<br><br>";
    public static final String SIGNATURE = "Yours truly,<br>Conference Smart App team";
    public static final String MESSAGE_GREETINGS = HELLO +
            "Thank you for choosing Conference Smart App!<br>" +
            "We have prepared some useful information for you:" +
            DOUBLE_ENTER +
            "<h4>Upcoming Conferences</h4>" +
            "Check <a href=" + URI + "controller?action=view-upcoming-events>upcoming events</a>, " +
            "register and enjoy listening to world famous Conference Smart App speakers." +
            DOUBLE_ENTER +
            "<h4>Your Conferences</h4>" +
            "Navigate through <a href=" + URI + "controller?action=view-visitors-events>your conferences</a>, " +
            "check date, location, topics." +
            DOUBLE_ENTER +
            SIGNATURE;
    public static final String MESSAGE_TOPIC_CHANGED = HELLO +
            "We have some important information for you:" +
            DOUBLE_ENTER +
            "Topic for you report at %s (%s %s) was changed to %s" +
            "<br>" +
            "You can check this report <a href=" + URI + "controller?action=view-event-by-speaker&event-id=%s>here</a>, " +
            DOUBLE_ENTER +
            SIGNATURE;
    public static final String MESSAGE_EVENT_DELETED = HELLO +
            "We have some important information for you:" +
            DOUBLE_ENTER +
            "The conference \"%s\" was canceled due to important circumstances" +
            DOUBLE_ENTER +
            SIGNATURE;

    private Email() {}
}