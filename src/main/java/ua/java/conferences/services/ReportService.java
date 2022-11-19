package ua.java.conferences.services;

import ua.java.conferences.entities.Report;
import ua.java.conferences.exceptions.*;

import java.util.List;

public interface ReportService extends Service<Report> {

    boolean setEventForReport(long eventId, long reportId) throws ServiceException;

    List<Report> getReportsFromEvent(long eventId) throws ServiceException;

    boolean setReportForSpeaker(long userId, long reportId) throws ServiceException;

    List<Report> getReportsFromSpeaker(long userId) throws ServiceException;

    boolean approveReport(long reportId) throws ServiceException;

    boolean acceptReport(long reportId) throws ServiceException;
}