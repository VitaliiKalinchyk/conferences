package ua.java.conferences.dto.response;

import java.util.Objects;

public class ReportResponseDTO {

    private long id;

    private String topic;

    private long speakerId;

    private String speakerName;

    private long eventId;

    private String title;

    private String date;

    private String location;

    public ReportResponseDTO(long id, String topic, long speakerId, String speakerName) {
        this.id = id;
        this.topic = topic;
        this.speakerId = speakerId;
        this.speakerName = speakerName;
    }

    public ReportResponseDTO(long id, String topic, long eventId, String title, String date, String location) {
        this.id = id;
        this.topic = topic;
        this.eventId = eventId;
        this.title = title;
        this.date = date;
        this.location = location;
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

    public String getSpeakerName() {
        return speakerName;
    }

    public void setSpeakerName(String speakerName) {
        this.speakerName = speakerName;
    }

    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReportResponseDTO that = (ReportResponseDTO) o;
        return id == that.id && speakerId == that.speakerId && eventId == that.eventId && topic.equals(that.topic)
                && Objects.equals(speakerName, that.speakerName) && Objects.equals(title, that.title)
                && Objects.equals(date, that.date) && Objects.equals(location, that.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, topic);
    }

    @Override
    public String toString() {
        return "ReportResponseDTO{" +
                "id=" + id +
                ", topic='" + topic + '\'' +
                ", speakerId=" + speakerId +
                ", speakerName='" + speakerName + '\'' +
                ", eventId=" + eventId +
                ", title='" + title + '\'' +
                ", date='" + date + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}