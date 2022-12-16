package ua.java.conferences.services;

import ua.java.conferences.dto.EventDTO;
import ua.java.conferences.exceptions.ServiceException;
import ua.java.conferences.utils.sorting.Sorting;

import java.util.List;

public interface EventService extends Service<EventDTO> {

    EventDTO addEvent(EventDTO eventDTO) throws ServiceException;

    EventDTO getByTitle(String title) throws ServiceException;

    List<EventDTO>  getSorted(Sorting sorting, String offset, String records) throws ServiceException;

    List<EventDTO> getSortedByUser(long userId, Sorting sorting, String offset, String records, String role) throws ServiceException;

    int getNumberOfRecords(Sorting sorting) throws ServiceException;

    int getNumberOfRecordsByUser(long id, Sorting sorting, String role) throws ServiceException;

    void setVisitorsCount(String eventIdString, String visitorsCountString) throws ServiceException;
}