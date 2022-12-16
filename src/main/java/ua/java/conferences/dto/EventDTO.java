package ua.java.conferences.dto;

import java.io.Serializable;
import java.util.Objects;

public class EventDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private long id;
    private String title;
    private String date;
    private String location;
    private String description;
    private int reports;
    private int registrations;
    private int visitors;

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

    public int getReports() {
        return reports;
    }

    public void setReports(int reports) {
        this.reports = reports;
    }

    public int getRegistrations() {
        return registrations;
    }

    public void setRegistrations(int registrations) {
        this.registrations = registrations;
    }

    public int getVisitors() {
        return visitors;
    }

    public void setVisitors(int visitors) {
        this.visitors = visitors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventDTO that = (EventDTO) o;
        return title.equals(that.title) && date.equals(that.date) && location.equals(that.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, date, location);
    }

    @Override
    public String toString() {
        return "EventDTO{" +
                "id=" + getId() +
                ", title='" + getTitle() + '\'' +
                ", date='" + getDate() + '\'' +
                ", location='" + getLocation() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", reports=" + getReports() +
                ", registrations=" + getRegistrations() +
                ", visitors=" + getVisitors() +
                '}';
    }

    public static class Builder {
        private final EventDTO event;

        public Builder() {
            this.event = new EventDTO();
        }

        public Builder setId(long id) {
            event.setId(id);
            return this;
        }

        public Builder setTitle(String title) {
            event.setTitle(title);
            return this;
        }

        public Builder setDate(String date) {
            event.setDate(date);
            return this;
        }

        public Builder setLocation(String location) {
            event.setLocation(location);
            return this;
        }

        public Builder setDescription(String description) {
            event.setDescription(description);
            return this;
        }

        public Builder setRegistrations(int registrations) {
            event.setRegistrations(registrations);
            return this;
        }

        public Builder setVisitors(int visitors) {
            event.setVisitors(visitors);
            return this;
        }

        public Builder setReports(int reports) {
            event.setReports(reports);
            return this;
        }

        public EventDTO get() {
            return event;
        }
    }
}