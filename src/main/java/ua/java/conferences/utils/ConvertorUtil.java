package ua.java.conferences.utils;

import ua.java.conferences.dto.*;
import ua.java.conferences.entities.*;
import ua.java.conferences.entities.role.Role;

import java.time.LocalDate;

public final class ConvertorUtil {

    private ConvertorUtil() {}

    public static User convertDTOToUser(UserDTO userDTO) {
        return User.builder()
                .id(userDTO.getId())
                .email(userDTO.getEmail())
                .name(userDTO.getName())
                .surname(userDTO.getSurname())
                .emailNotification(userDTO.isNotification())
                .build();
    }

    public static UserDTO convertUserToDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .surname(user.getSurname())
                .notification(user.isEmailNotification())
                .role(String.valueOf(Role.getRole(user.getRoleId())))
                .build();
    }

    public static Report convertDTOToReport(ReportDTO reportDTO) {
        long speakerId = reportDTO.getSpeakerId();
        return Report.builder()
                .id(reportDTO.getId())
                .topic(reportDTO.getTopic())
                .speaker(speakerId != 0 ? User.builder().id(speakerId).build() : null)
                .event(Event.builder().id(reportDTO.getEventId()).build())
                .build();
    }

    public static ReportDTO convertReportToDTO(Report report) {
        User speaker = report.getSpeaker();
        Event event = report.getEvent();
        return ReportDTO.builder()
                .id(report.getId())
                .topic(report.getTopic())
                .speakerId(speaker != null ?  speaker.getId() : 0)
                .speakerName(speaker != null ?  speaker.getName() + " " + speaker.getSurname() : "")
                .eventId(event != null ? event.getId() : 0)
                .title(event != null ? event.getTitle() : "")
                .date(event != null ? event.getDate().toString() : "")
                .location(event != null ? event.getLocation() : "")
                .build();
    }

    public static Event convertDTOToEvent(ua.java.conferences.dto.EventDTO eventDTO) {
        return Event.builder()
                .id(eventDTO.getId())
                .title(eventDTO.getTitle())
                .date(LocalDate.parse(eventDTO.getDate()))
                .location(eventDTO.getLocation())
                .description(eventDTO.getDescription())
                .build();
    }

    public static EventDTO convertEventToDTO(Event event) {
        return EventDTO.builder()
                .id(event.getId())
                .title(event.getTitle())
                .date(event.getDate().toString())
                .location(event.getLocation())
                .description(event.getDescription())
                .registrations(event.getRegistrations())
                .visitors(event.getVisitors())
                .reports(event.getReports())
                .build();
    }
}