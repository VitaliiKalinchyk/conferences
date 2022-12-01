package ua.java.conferences.dto.request;

import java.io.Serializable;
import java.util.Objects;

public class EventRequestDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private long id;

    private String title;

    private String date;

    private String location;

    private String description;

    public EventRequestDTO(long id, String title, String date, String location, String description) {
        setId(id);
        setTitle(title);
        setDate(date);
        setLocation(location);
        setDescription(description);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventRequestDTO that = (EventRequestDTO) o;
        return id == that.id && title.equals(that.title) && date.equals(that.date)
                && location.equals(that.location) && description.equals(that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, date, location, description);
    }

    @Override
    public String toString() {
        return "EventRequestDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", date='" + date + '\'' +
                ", location='" + location + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}