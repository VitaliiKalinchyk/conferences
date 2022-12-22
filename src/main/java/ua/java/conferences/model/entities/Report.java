package ua.java.conferences.model.entities;

import lombok.*;
import java.io.Serializable;

@Data
@Builder
public class Report implements Serializable {
    private static final long serialVersionUID = 1L;
    private long id;
    private String topic;
    @EqualsAndHashCode.Exclude private Event event;
    @EqualsAndHashCode.Exclude private User speaker;
}