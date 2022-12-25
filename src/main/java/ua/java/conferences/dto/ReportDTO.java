package ua.java.conferences.dto;

import lombok.*;
import java.io.Serializable;

@Data
@EqualsAndHashCode(of = {"id", "topic"})
@Builder
public class ReportDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private long id;
    private String topic;
    private long speakerId;
    private String speakerEmail;
    private String speakerName;
    private long eventId;
    private String title;
    private String date;
    private String location;
}