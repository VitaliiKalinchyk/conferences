package ua.java.conferences.dao;

import ua.java.conferences.entity.*;
import ua.java.conferences.exception.DAOException;

import java.util.List;

public interface ReportDAO extends EntityDAO<Report> {

    boolean setEventForReport(Event event, Report report) throws DAOException;

    List<Report> getReportsFromEvent(Event event) throws DAOException;

    boolean setReportForSpeaker(User user, Report report) throws DAOException;

    List<Report> getReportsFromSpeaker(User user) throws DAOException;
}