package ua.java.conferences.dto;

import lombok.*;
import java.io.Serializable;

@Data
@EqualsAndHashCode(of = {"title", "date", "location"})
@Builder
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
}