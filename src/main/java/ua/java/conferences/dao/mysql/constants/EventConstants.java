package ua.java.conferences.dao.mysql.constants;


public final class EventConstants {

    public static final String ADD_EVENT = "INSERT INTO event (title, date, location, description) VALUES (?,?,?,?)";

    public static final String GET_EVENTS = "SELECT * FROM event";

    public static final String GET_EVENT = "SELECT * FROM event WHERE title=?";

    public static final String EDIT_EVENT = "UPDATE event SET title=?, date=?, location=?, description=? WHERE id=?";

    public static final String DELETE_EVENT = "DELETE FROM event WHERE id=?";

    public static final String SET_VISITORS = "UPDATE event SET visitors=? WHERE id=?";

    public static final String GET_USERS_EVENTS =
            "SELECT * FROM event JOIN user_has_event ON event.id=user_has_event.event_id WHERE user_id=?";

    public static final String GET_SPEAKERS_EVENTS =
            "SELECT * FROM event JOIN report ON event.id=report.event_id WHERE user_id=?";

    public static final String GET_REPORT_EVENT =
            "SELECT * FROM event JOIN report ON event.id=report.event_id WHERE report.id=?";

    private EventConstants() {}
}