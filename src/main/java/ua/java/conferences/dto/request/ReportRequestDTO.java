package ua.java.conferences.dto.request;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReportRequestDTO that = (ReportRequestDTO) o;
        return id == that.id && speakerId == that.speakerId && eventId == that.eventId && topic.equals(that.topic);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, topic, speakerId, eventId);
    }
}