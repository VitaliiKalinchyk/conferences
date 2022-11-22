package ua.java.conferences.dao.mysql.constants;


public final class EventConstants {

    private EventConstants() {}

    public static final String ADD_EVENT = "INSERT INTO event (title, date, location, description) VALUES (?,?,?,?)";

    private static final String UTIL_GET_EVENT = "SELECT id, title, date, location, description, " +
            "COUNT(user_id) AS registrations, visitors FROM event LEFT JOIN user_has_event " +
            "ON id=user_has_event.event_id ";

    public static final String GET_EVENT_BY_ID =  UTIL_GET_EVENT +"WHERE id=? GROUP BY id";

    public static final String GET_EVENT_BY_TITLE =  UTIL_GET_EVENT +"WHERE title=? GROUP BY id";

    public static final String GET_EVENTS =  UTIL_GET_EVENT +"GROUP BY id";

    public static final String GET_SORTED_EVENTS = UTIL_GET_EVENT + "WHERE date %s now() GROUP BY id ORDER BY %s %s";

    public static final String GET_VISITORS_EVENTS =
            "SELECT * FROM event JOIN user_has_event ON event.id=user_has_event.event_id WHERE user_id=?";

    public static final String GET_SPEAKERS_EVENTS =
            "SELECT * FROM event JOIN report ON event.id=report.event_id WHERE user_id=?";

    public static final String EDIT_EVENT = "UPDATE event SET title=?, date=?, location=?, description=? WHERE id=?";

    public static final String DELETE_EVENT = "DELETE event, user_has_event, report FROM event LEFT JOIN" +
            " user_has_event ON event.id=user_has_event.event_id LEFT JOIN report" +
            " ON event.id=report.event_id WHERE event.id=?";

    public static final String SET_VISITORS = "UPDATE event SET visitors=? WHERE id=?";
}