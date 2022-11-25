package ua.java.conferences.dto.response;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReportResponseDTO that = (ReportResponseDTO) o;
        return id == that.id && speakerId == that.speakerId && topic.equals(that.topic) && speakerName.equals(that.speakerName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, topic, speakerId);
    }
}