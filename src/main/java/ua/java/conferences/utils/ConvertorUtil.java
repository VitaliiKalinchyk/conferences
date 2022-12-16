package ua.java.conferences.utils;

import ua.java.conferences.dto.*;
import ua.java.conferences.entities.*;
import ua.java.conferences.entities.role.Role;

import java.time.LocalDate;

import static ua.java.conferences.utils.PasswordHashUtil.*;

public final class ConvertorUtil {

    private ConvertorUtil() {}

    public static User convertDTOToUser(UserDTO userDTO) {
        return new User.Builder()
                .setId(userDTO.getId())
                .setEmail(userDTO.getEmail())
                .setPassword(encode(userDTO.getPassword()))
                .setName(userDTO.getName())
                .setSurname(userDTO.getSurname())
                .setEmailNotification(userDTO.isNotification())
                .get();
    }

    public static UserDTO convertUserToDTO(User user) {
        return new UserDTO.Builder()
                .setId(user.getId())
                .setEmail(user.getEmail())
                .setName(user.getName())
                .setSurname(user.getSurname())
                .setNotification(user.isEmailNotification())
                .setRole(String.valueOf(Role.getRole(user.getRoleId())))
                .get();
    }

    public static Report convertDTOToReport(ReportDTO reportDTO) {
        long speakerId = reportDTO.getSpeakerId();
        return new Report.Builder()
                .setId(reportDTO.getId())
                .setTopic(reportDTO.getTopic())
                .setSpeaker(speakerId != 0 ? new User.Builder().setId(speakerId).get() : null)
                .setEvent(new Event.Builder().setId(reportDTO.getEventId()).get())
                .get();
    }

    public static ReportDTO convertReportToDTO(Report report) {
        User speaker = report.getSpeaker();
        Event event = report.getEvent();
        return new ReportDTO.Builder()
                .setId(report.getId())
                .setTopic(report.getTopic())
                .setSpeakerId(speaker != null ?  speaker.getId() : 0)
                .setSpeakerName(speaker != null ?  speaker.getName() + " " + speaker.getSurname() : "")
                .setEventId(event != null ? event.getId() : 0)
                .setTitle(event != null ? event.getTitle() : "")
                .setDate(event != null ? event.getDate().toString() : "")
                .setLocation(event != null ? event.getLocation() : "")
                .get();
    }

    public static Event convertDTOToEvent(ua.java.conferences.dto.EventDTO eventDTO) {
        return new Event.Builder()
                .setId(eventDTO.getId())
                .setTitle(eventDTO.getTitle())
                .setDate(LocalDate.parse(eventDTO.getDate()))
                .setLocation(eventDTO.getLocation())
                .setDescription(eventDTO.getDescription())
                .get();
    }

    public static EventDTO convertEventToDTO(Event event) {
        return new EventDTO.Builder()
                .setId(event.getId())
                .setTitle(event.getTitle())
                .setDate(event.getDate().toString())
                .setLocation(event.getLocation())
                .setDescription(event.getDescription())
                .setRegistrations(event.getRegistrations())
                .setVisitors(event.getVisitors())
                .setReports(event.getReports())
                .get();
    }
}