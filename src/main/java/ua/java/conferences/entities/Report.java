package ua.java.conferences.entities;

import java.io.Serializable;
import java.util.Objects;

public class Report implements Serializable {
    private static final long serialVersionUID = 1L;
    private long id;
    private String topic;
    private Event event;
    private User speaker;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTopic() {
        return this.topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public User getSpeaker() {
        return speaker;
    }

    public void setSpeaker(User speaker) {
        this.speaker = speaker;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Report report = (Report) o;
        return id == report.id && topic.equals(report.topic);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Report{" +
                "id=" + id +
                ", topic='" + topic + '\'' +
                ", event=" + event +
                ", speaker=" + speaker +
                '}';
    }

    public static class Builder {
        private final Report report;

        public Builder() {
            this.report = new Report();
        }

        public Builder setId(long id) {
            report.setId(id);
            return this;
        }

        public Builder setTopic(String topic) {
            report.setTopic(topic);
            return this;
        }

        public Builder setEvent(Event event) {
            report.setEvent(event);
            return this;
        }

        public Builder setSpeaker(User speaker) {
            report.setSpeaker(speaker);
            return this;
        }

        public Report get() {
            return report;
        }
    }
}