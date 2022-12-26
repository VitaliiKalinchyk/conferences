package ua.java.conferences.utils.constants;

public class Email {
    private static final String URI = "http://localhost:8080/conferences/";
    public static final String SUBJECT_GREETINGS = "Welcome to Conference Smart App!";
    public static final String SUBJECT_NOTIFICATION = "Conference Smart App notification!";
    public static final String HELLO = "Hello %s,<br>";
    public static final String INFORMATION = "We have some important information for you:";
    public static final String SIGNATURE = "Yours truly,<br>Conference Smart App team";
    public static final String DOUBLE_ENTER = "<br><br>";
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
            INFORMATION +
            DOUBLE_ENTER +
            "Topic for you report at %s (%s %s) was changed to %s" +
            "<br>" +
            "You can check this report <a href=" + URI + "controller?action=view-event-by-speaker&event-id=%s>here</a>, " +
            DOUBLE_ENTER +
            SIGNATURE;
    public static final String MESSAGE_EVENT_CHANGED_VISITOR = HELLO +
            INFORMATION +
            DOUBLE_ENTER +
            "The conference \"%s\" was changed due to important circumstances<br>" +
            "Conference date: %s <br>" +
            "Conference location: %s <br>" +
            "Conference description: %s" +
            DOUBLE_ENTER +
            "You can also check this conference " +
            "<a href=" + URI + "controller?action=view-event-by-visitor&event-id=%s>here</a>, " +
            DOUBLE_ENTER +
            SIGNATURE;
    public static final String MESSAGE_EVENT_CHANGED_SPEAKER = HELLO +
            INFORMATION +
            DOUBLE_ENTER +
            "The conference \"%s\" was changed due to important circumstances<br>" +
            "Conference date: %s <br>" +
            "Conference location: %s <br>" +
            "Conference description: %s" +
            DOUBLE_ENTER +
            "You can also check this conference " +
            "<a href=" + URI + "controller?action=view-event-by-speaker&event-id=%s>here</a>, " +
            DOUBLE_ENTER +
            SIGNATURE;
    public static final String MESSAGE_EVENT_DELETED = HELLO +
            INFORMATION +
            DOUBLE_ENTER +
            "The conference \"%s\" was canceled due to important circumstances" +
            DOUBLE_ENTER +
            SIGNATURE;
    public static final String MESSAGE_REPORT_DELETED = HELLO +
            INFORMATION +
            DOUBLE_ENTER +
            "The report \"%s\" at %s (%s %s)  was canceled due to important circumstances" +
            DOUBLE_ENTER +
            SIGNATURE;
    public static final String MESSAGE_REMOVE_SPEAKER = HELLO +
            INFORMATION +
            DOUBLE_ENTER +
            "You were removed as speaker at report \"%s\" at %s (%s %s) due to important circumstances" +
            DOUBLE_ENTER +
            SIGNATURE;
    public static final String MESSAGE_SET_SPEAKER = HELLO +
            INFORMATION +
            DOUBLE_ENTER +
            "You were set as speaker at report \"%s\" at %s (%s %s) due to important circumstances" +
            DOUBLE_ENTER +
            SIGNATURE;
    public static final String MESSAGE_OFFER_REPORT = HELLO +
            INFORMATION +
            DOUBLE_ENTER +
            "Speaker %s offered report \"%s\" for %s" +
            DOUBLE_ENTER +
            SIGNATURE;
    public static final String MESSAGE_SET_SPEAKER_BY_SPEAKER = HELLO +
            INFORMATION +
            DOUBLE_ENTER +
            "%s set himself as speaker for  report \"%s\" at %s" +
            DOUBLE_ENTER +
            SIGNATURE;
    public static final String MESSAGE_REMOVE_SPEAKER_BY_SPEAKER = HELLO +
            INFORMATION +
            DOUBLE_ENTER +
            "%s remove himself as speaker for  report \"%s\" at %s" +
            DOUBLE_ENTER +
            SIGNATURE;
    public static final String MESSAGE_RESET_PASSWORD = HELLO +
            INFORMATION +
            DOUBLE_ENTER +
            "Your temporary password is %s. Do not forget to change it in your profile!" +
            DOUBLE_ENTER +
            SIGNATURE;
    private Email() {}
}