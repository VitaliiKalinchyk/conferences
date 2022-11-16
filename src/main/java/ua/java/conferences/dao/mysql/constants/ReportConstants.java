package ua.java.conferences.dao.mysql.constants;

public final class ReportConstants {


    private ReportConstants() {}

    public static final String ADD_REPORT = "INSERT INTO report (topic, accepted, approved) VALUES (?,?,?)";

    public static final String GET_REPORTS = "SELECT * FROM report";

    public static final String GET_REPORT_BY_ID = "SELECT * FROM report WHERE id=?";

    public static final String EDIT_REPORT = "UPDATE report SET topic=?, accepted=?, approved=? WHERE id=?";

    public static final String DELETE_REPORT = "DELETE FROM report WHERE id=?";

    public static final String SET_EVENT = "UPDATE report SET event_id=? WHERE id=?";

    public static final String GET_EVENTS_REPORT = "SELECT * FROM report WHERE event_id=?";

    public static final String SET_REPORT = "UPDATE report SET user_id=? WHERE id=?";

    public static final String GET_SPEAKERS_REPORT = "SELECT * FROM report WHERE user_id=?";
}