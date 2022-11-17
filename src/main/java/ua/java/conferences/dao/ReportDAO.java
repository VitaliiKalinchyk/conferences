package ua.java.conferences.dao;

import ua.java.conferences.entity.*;
import ua.java.conferences.exception.DAOException;

import java.util.List;

public interface ReportDAO extends EntityDAO<Report> {

    boolean setEventForReport(long eventId, long reportId) throws DAOException;

    List<Report> getReportsFromEvent(long eventId) throws DAOException;

    boolean setReportForSpeaker(long userId, long reportId) throws DAOException;

    List<Report> getReportsFromSpeaker(long userId) throws DAOException;
}