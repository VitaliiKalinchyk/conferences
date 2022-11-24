package ua.java.conferences.dto.request;

public class ReportRequestDTO {

    public final long id;

    public final String topic;

    public final long speakerId;

    public final long eventId;

    public ReportRequestDTO(long id, String topic, long speakerId, long eventId) {
        this.id = id;
        this.topic = topic;
        this.speakerId = speakerId;
        this.eventId = eventId;
    }
}