package ua.java.conferences.dto.response;

public class ReportResponseDTO {

    public final long id;

    public final String topic;

    public final long speakerId;

    public final String speakerName;

    public ReportResponseDTO(long id, String topic, long speakerId, String speakerName) {
        this.id = id;
        this.topic = topic;
        this.speakerId = speakerId;
        this.speakerName = speakerName;
    }
}