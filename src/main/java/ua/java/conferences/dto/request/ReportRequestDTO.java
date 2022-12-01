package ua.java.conferences.dto.request;

import java.io.Serializable;
import java.util.Objects;

public class ReportRequestDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private long id;

    private String topic;

    private long speakerId;

    private long eventId;

    public ReportRequestDTO(long id, String topic, long speakerId, long eventId) {
        setId(id);
        setTopic(topic);
        setSpeakerId(speakerId);
        setEventId(eventId);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public long getSpeakerId() {
        return speakerId;
    }

    public void setSpeakerId(long speakerId) {
        this.speakerId = speakerId;
    }

    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
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

    @Override
    public String toString() {
        return "ReportRequestDTO{" +
                "id=" + id +
                ", topic='" + topic + '\'' +
                ", speakerId=" + speakerId +
                ", eventId=" + eventId +
                '}';
    }
}