package ua.java.conferences.dto.response;

import java.io.Serializable;
import java.util.Objects;

public class EventResponseDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private long id;

    private String title;

    private String date;

    private String location;

    private String description;

    private int reports;

    private int registrations;

    private int visitors;

    public EventResponseDTO(long id, String title, String date, String location, String description) {
        setId(id);
        setTitle(title);
        setDate(date);
        setLocation(location);
        setDescription(description);
    }

    public EventResponseDTO(long id, String title, String date, String location,
                            int reports, int registrations, int visitors) {
        setId(id);
        setTitle(title);
        setDate(date);
        setLocation(location);
        setReports(reports);
        setRegistrations(registrations);
        setVisitors(visitors);
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
}