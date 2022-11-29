package ua.java.conferences.services;

import ua.java.conferences.dto.request.EventRequestDTO;
import ua.java.conferences.dto.response.*;
import ua.java.conferences.exceptions.ServiceException;

import java.util.List;

public interface EventService extends Service<EventResponseDTO> {

    EventResponseDTO createEvent(EventRequestDTO eventDTO) throws ServiceException;

    EventResponseDTO searchEvent(String title) throws ServiceException;

    List<EventResponseDTO> viewUsersEvents(long userId) throws ServiceException;

    List<EventResponseDTO> viewSpeakersEvents(long speakerId) throws ServiceException;

    List<EventResponseDTO> viewSortedEventsByUser(String sortField, String order) throws ServiceException;

    List<EventResponseDTO> viewSortedEventsByModerator(String filter, String sortField, String order) throws ServiceException;

    EventResponseDTO editEvent(EventRequestDTO eventDTO) throws ServiceException;

    void setVisitorsCount(long eventId, int visitorsCount) throws ServiceException;
}