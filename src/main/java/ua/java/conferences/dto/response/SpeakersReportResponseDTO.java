package ua.java.conferences.dto.response;

public class SpeakersReportResponseDTO {

    public final long id;

    public final String topic;

    public final long eventId;

    public final String title;

    public final String date;

    public final String location;

    public SpeakersReportResponseDTO(long id, String topic, long eventId, String title, String date, String location) {
        this.id = id;
        this.topic = topic;
        this.eventId = eventId;
        this.title = title;
        this.date = date;
        this.location = location;
    }
}