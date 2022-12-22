package ua.java.conferences.model.dao;

import ua.java.conferences.exceptions.DAOException;
import ua.java.conferences.model.entities.Report;

import java.util.*;

public interface ReportDAO extends EntityDAO<Report> {

    List<Report> getSpeakersReports(long speakerId) throws DAOException;

    List<Report> getEventsReports(long eventId) throws DAOException;

    void setSpeaker(long reportId, long speakerId) throws DAOException;

    void deleteSpeaker(long reportId) throws DAOException;
}