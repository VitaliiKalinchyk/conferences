package ua.java.conferences.model.entities;

import lombok.*;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(of = {"id", "title"})
@Builder
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
}