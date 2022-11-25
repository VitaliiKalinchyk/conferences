package ua.java.conferences.dto.response;

import java.util.Objects;

public class FullEventResponseDTO {

    public final long id;

    public final String title;

    public final String date;

    public final String location;

    public final int reports;

    public final int registrations;

    public final int visitors;

    public FullEventResponseDTO(long id, String title, String date, String location,
                                int reports, int registrations, int visitors) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.location = location;
        this.reports = reports;
        this.registrations = registrations;
        this.visitors = visitors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FullEventResponseDTO that = (FullEventResponseDTO) o;
        return id == that.id && reports == that.reports && registrations == that.registrations && visitors == that.visitors && title.equals(that.title) && date.equals(that.date) && location.equals(that.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, date, location, reports, registrations, visitors);
    }
}