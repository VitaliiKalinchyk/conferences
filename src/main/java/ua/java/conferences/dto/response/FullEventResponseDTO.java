package ua.java.conferences.dto.response;

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
}