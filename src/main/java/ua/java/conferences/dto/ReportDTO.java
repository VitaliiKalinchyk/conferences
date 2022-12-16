package ua.java.conferences.dto;

import java.io.Serializable;
import java.util.Objects;

public class ReportDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private long id;
    private String topic;
    private long speakerId;
    private String speakerName;
    private long eventId;
    private String title;
    private String date;
    private String location;

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
        ReportDTO that = (ReportDTO) o;
        return id == that.id && topic.equals(that.topic);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, topic);
    }

    @Override
    public String toString() {
        return "ReportDTO{" +
                "id=" + getId() +
                ", topic='" + getTopic() + '\'' +
                ", speakerId=" + getSpeakerId() +
                ", speakerName='" + getSpeakerName() + '\'' +
                ", eventId=" + getEventId() +
                ", title='" + getTitle() + '\'' +
                ", date='" + getDate() + '\'' +
                ", location='" + getLocation() + '\'' +
                '}';
    }

    public static class Builder {
        private final ReportDTO report;

        public Builder() {
            this.report = new ReportDTO();
        }

        public Builder setId(long id) {
            report.setId(id);
            return this;
        }

        public Builder setTopic(String topic) {
            report.setTopic(topic);
            return this;
        }

        public Builder setSpeakerId(long speakerId) {
            report.setSpeakerId(speakerId);
            return this;
        }

        public Builder setSpeakerName(String setSpeakerName) {
            report.setSpeakerName(setSpeakerName);
            return this;
        }

        public Builder setEventId(long eventId) {
            report.setEventId(eventId);
            return this;
        }

        public Builder setTitle(String title) {
            report.setTitle(title);
            return this;
        }

        public Builder setDate(String date) {
            report.setTitle(date);
            return this;
        }

        public Builder setLocation(String location) {
            report.setLocation(location);
            return this;
        }
        public ReportDTO get() {
            return report;
        }
    }
}