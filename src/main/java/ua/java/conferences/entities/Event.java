package ua.java.conferences.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class Event implements Serializable {

    private static final long serialVersionUID = 1L;

    private long id;

    private String title;

    private LocalDate date;

    private String location;

    private String description;

    private int registrations;

    private int visitors;

    private int reports;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRegistrations() {
        return registrations;
    }

    public void setRegistrations(int registrations) {
        this.registrations = registrations;
    }

    public int getVisitors() {
        return this.visitors;
    }

    public void setVisitors(int visitors) {
        this.visitors = visitors;
    }

    public int getReports() {
        return reports;
    }

    public void setReports(int reports) {
        this.reports = reports;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return id == event.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", date=" + date +
                ", location='" + location + '\'' +
                ", description='" + description + '\'' +
                ", registrations=" + registrations +
                ", visitors=" + visitors +
                ", reports=" + reports +
                '}';
    }

    public static class EventBuilder {

        private final Event event;

        public EventBuilder() {
            this.event = new Event();
        }

        public EventBuilder setId(long id) {
            event.setId(id);
            return this;
        }

        public EventBuilder setTitle(String title) {
            event.setTitle(title);
            return this;
        }

        public EventBuilder setDate(LocalDate date) {
            event.setDate(date);
            return this;
        }

        public EventBuilder setLocation(String location) {
            event.setLocation(location);
            return this;
        }

        public EventBuilder setDescription(String description) {
            event.setDescription(description);
            return this;
        }

        public EventBuilder setRegistrations(int registrations) {
            event.setRegistrations(registrations);
            return this;
        }

        public EventBuilder setVisitors(int visitors) {
            event.setVisitors(visitors);
            return this;
        }

        public EventBuilder setReports(int reports) {
            event.setReports(reports);
            return this;
        }

        public Event get() {
            return event;
        }
    }
}