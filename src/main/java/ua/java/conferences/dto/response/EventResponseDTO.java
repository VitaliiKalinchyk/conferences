package ua.java.conferences.dto.response;

import java.util.Objects;

public class EventResponseDTO {

    public final long id;

    public final String title;

    public final String date;

    public final String location;

    public final String description;

    public EventResponseDTO(long id, String title, String date, String location, String description) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.location = location;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventResponseDTO that = (EventResponseDTO) o;
        return id == that.id && title.equals(that.title) && date.equals(that.date)
                && location.equals(that.location) && description.equals(that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, date, location, description);
    }
}