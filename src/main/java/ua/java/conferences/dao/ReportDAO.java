package ua.java.conferences.dao;

import ua.java.conferences.entity.*;
import ua.java.conferences.exception.DBException;

import java.util.List;

public interface ReportDAO extends EntityDAO<Report> {

    boolean setEventForReport(Event var1, Report var2) throws DBException;

    List<Report> getReportsFromEvent(Event var1) throws DBException;

    boolean setReportForSpeaker(User var1, Report var2) throws DBException;

    List<Report> getReportsFromSpeaker(User var1) throws DBException;
}