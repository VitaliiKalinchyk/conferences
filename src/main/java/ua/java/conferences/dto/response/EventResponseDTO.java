package ua.java.conferences.dto.response;

import java.util.Objects;

public class EventResponseDTO {

    private long id;

    private String title;

    private String date;

    private String location;

    private String description;

    private int reports;

    private int registrations;

    private int visitors;

    public EventResponseDTO(long id, String title, String date, String location, String description) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.location = location;
        this.description = description;
    }

    public EventResponseDTO(long id, String title, String date, String location,
                            int reports, int registrations, int visitors) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.location = location;
        this.reports = reports;
        this.registrations = registrations;
        this.visitors = visitors;
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
        EventResponseDTO that = (EventResponseDTO) o;
        return id == that.id && reports == that.reports && registrations == that.registrations
                && visitors == that.visitors && title.equals(that.title) && date.equals(that.date)
                && location.equals(that.location) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, date, location, reports, registrations, visitors);
    }

    @Override
    public String toString() {
        return "EventResponseDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", date='" + date + '\'' +
                ", location='" + location + '\'' +
                ", description='" + description + '\'' +
                ", reports=" + reports +
                ", registrations=" + registrations +
                ", visitors=" + visitors +
                '}';
    }
}