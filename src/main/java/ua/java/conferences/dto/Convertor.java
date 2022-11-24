package ua.java.conferences.dto;

import ua.java.conferences.dto.request.*;
import ua.java.conferences.dto.response.*;
import ua.java.conferences.entities.*;
import ua.java.conferences.entities.role.Role;

import java.time.LocalDate;

public final class Convertor {

    private Convertor() {}

    public static User convertDTOToUser(UserRequestDTO userDTO) {
        return new User.UserBuilder()
                .setId(userDTO.id)
                .setEmail(userDTO.email)
                .setPassword(userDTO.password)
                .setName(userDTO.name)
                .setSurname(userDTO.surname)
                .setEmailNotification(userDTO.notification)
                .get();
    }

    public static UserResponseDTO convertUserToDTO(User user) {
        return new UserResponseDTO(user.getId(), user.getEmail(), user.getName(), user.getSurname(),
                user.isEmailNotification(), Role.ADMIN.getName(user.getRoleId()));
    }

    public static Report convertDTOToReport(ReportRequestDTO reportDTO) {
        long speakerId = reportDTO.speakerId;
        User speaker = null;
        if (speakerId != 0) {
            speaker = new User.UserBuilder().setId(speakerId).get();
        }
        return new Report.ReportBuilder()
                .setId(reportDTO.id)
                .setTopic(reportDTO.topic)
                .setSpeaker(speaker)
                .setEvent(new Event.EventBuilder().setId(reportDTO.eventId).get())
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

    public static SpeakersReportResponseDTO convertSpeakersReportToDTO(Report report) {
        Event event = report.getEvent();
        return new SpeakersReportResponseDTO(report.getId(), report.getTopic(),
                event.getId(), event.getTitle(), event.getDate().toString(), event.getLocation());
    }

    public static Event convertDTOToEvent(EventRequestDTO eventDTO) {
        return new Event.EventBuilder()
                .setId(eventDTO.id)
                .setTitle(eventDTO.title)
                .setDate(LocalDate.parse(eventDTO.date))
                .setLocation(eventDTO.location)
                .setDescription(eventDTO.description)
                .get();
    }

    public static EventResponseDTO convertEventToDTO(Event event) {
        return new EventResponseDTO(event.getId(), event.getTitle(),
                event.getDate().toString(), event.getLocation(), event.getDescription());
    }

    public static  FullEventResponseDTO  convertEventToFullDTO (Event event) {
        return new FullEventResponseDTO(event.getId(), event.getTitle(),
                event.getDate().toString(), event.getLocation(), event.getReports(),
                event.getRegistrations(), event.getVisitors());
    }
}