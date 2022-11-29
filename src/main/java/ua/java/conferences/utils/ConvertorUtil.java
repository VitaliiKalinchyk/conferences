package ua.java.conferences.utils;

import ua.java.conferences.dto.request.*;
import ua.java.conferences.dto.response.*;
import ua.java.conferences.entities.*;
import ua.java.conferences.entities.role.Role;

import java.time.LocalDate;

import static ua.java.conferences.utils.PasswordHashUtil.*;

public final class ConvertorUtil {

    private ConvertorUtil() {}

    public static User convertDTOToUser(UserRequestDTO userDTO) {
        return new User.UserBuilder()
                .setId(userDTO.getId())
                .setEmail(userDTO.getEmail())
                .setPassword(encode(userDTO.getPassword()))
                .setName(userDTO.getName())
                .setSurname(userDTO.getSurname())
                .setEmailNotification(userDTO.isNotification())
                .get();
    }

    public static UserResponseDTO convertUserToDTO(User user) {
        return new UserResponseDTO(user.getId(), user.getEmail(), user.getName(), user.getSurname(),
                user.isEmailNotification(), Role.getRole(user.getRoleId()).name());
    }

    public static Report convertDTOToReport(ReportRequestDTO reportDTO) {
        long speakerId = reportDTO.getSpeakerId();
        User speaker = null;
        if (speakerId != 0) {
            speaker = new User.UserBuilder().setId(speakerId).get();
        }
        long eventId = reportDTO.getEventId();
        Event event = new Event.EventBuilder().setId(eventId).get();
        return new Report.ReportBuilder()
                .setId(reportDTO.getId())
                .setTopic(reportDTO.getTopic())
                .setSpeaker(speaker)
                .setEvent(event)
                .get();
    }

    public static ReportResponseDTO convertReportToDTO(Report report) {
        User speaker = report.getSpeaker();
        long speakerId = 0;
        String speakersName = "";
        if (speaker != null) {
            speakerId = speaker.getId();
            speakersName = speaker.getName() + " " + speaker.getSurname();
        }
        return new ReportResponseDTO(report.getId(), report.getTopic(), speakerId, speakersName);
    }

    public static ReportResponseDTO convertSpeakersReportToDTO(Report report) {
        Event event = report.getEvent();
        return new ReportResponseDTO(report.getId(), report.getTopic(),
                event.getId(), event.getTitle(), event.getDate().toString(), event.getLocation());
    }

    public static Event convertDTOToEvent(EventRequestDTO eventDTO) {
        return new Event.EventBuilder()
                .setId(eventDTO.getId())
                .setTitle(eventDTO.getTitle())
                .setDate(LocalDate.parse(eventDTO.getDate()))
                .setLocation(eventDTO.getLocation())
                .setDescription(eventDTO.getDescription())
                .get();
    }

    public static EventResponseDTO convertEventToDTO(Event event) {
        return new EventResponseDTO(event.getId(), event.getTitle(),
                event.getDate().toString(), event.getLocation(), event.getDescription());
    }

    public static EventResponseDTO convertEventToFullDTO (Event event) {
        return new EventResponseDTO(event.getId(), event.getTitle(),
                event.getDate().toString(), event.getLocation(), event.getReports(),
                event.getRegistrations(), event.getVisitors());
    }
}