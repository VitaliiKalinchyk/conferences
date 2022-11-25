package ua.java.conferences.dto.response;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpeakersReportResponseDTO that = (SpeakersReportResponseDTO) o;
        return id == that.id && eventId == that.eventId && topic.equals(that.topic) && title.equals(that.title) && date.equals(that.date) && location.equals(that.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, topic, eventId, title, date, location);
    }
}