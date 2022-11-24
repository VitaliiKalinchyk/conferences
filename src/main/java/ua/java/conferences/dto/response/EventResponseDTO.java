package ua.java.conferences.dto.response;

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
}