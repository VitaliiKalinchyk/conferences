package ua.java.conferences.services;

import ua.java.conferences.dto.EventDTO;
import ua.java.conferences.entities.role.Role;
import ua.java.conferences.exceptions.ServiceException;

import java.util.List;

public interface EventService extends Service<EventDTO> {

    EventDTO addEvent(EventDTO eventDTO) throws ServiceException;

    EventDTO getByTitle(String title) throws ServiceException;

    List<EventDTO>  getSorted(String query) throws ServiceException;

    List<EventDTO> getSortedByUser(String query, Role role) throws ServiceException;

    int getNumberOfRecords(String filter, Role role) throws ServiceException;

    void setVisitorsCount(String eventIdString, String visitorsCountString) throws ServiceException;
}