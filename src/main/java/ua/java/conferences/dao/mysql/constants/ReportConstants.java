package ua.java.conferences.dao.mysql.constants;

public final class ReportConstants {


    private ReportConstants() {}

    public static final String ADD_REPORT = "INSERT INTO report (topic, event_id, user_id) VALUES (?, ?, ?)";

    public static final String GET_REPORTS = "SELECT report.id, report.topic, report.event_id," +
            " report.user_id, user.name, user.surname FROM report " +
            "LEFT JOIN user ON report.user_id=user.id";

    public static final String GET_REPORT_BY_ID = GET_REPORTS +" WHERE report.id = ?";

    public static final String GET_EVENTS_REPORTS = GET_REPORTS + " WHERE event.id=?";

    public static final String EDIT_REPORT = "UPDATE report SET topic=? WHERE id=?";

    public static final String DELETE_REPORT = "DELETE FROM report WHERE id=?";

    public static final String SET_SPEAKER = "UPDATE report SET user_id=? WHERE id=?";

    public static final String GET_SPEAKERS_REPORTS = "SELECT report.id, report.topic, " +
            "event.title, event.date, event.location FROM report JOIN event ON report.event_id=event.id " +
            "WHERE report.user_id=? AND event.date > now()";
}