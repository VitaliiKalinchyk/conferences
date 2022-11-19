package ua.java.conferences.services.implementation;

import ua.java.conferences.dao.ReportDAO;
import ua.java.conferences.entities.Report;
import ua.java.conferences.exceptions.*;
import ua.java.conferences.services.ReportService;

import java.util.List;

public class ReportServiceImpl implements ReportService {


    private final ReportDAO reportDAO;

    public ReportServiceImpl(ReportDAO reportDAO) {
        this.reportDAO = reportDAO;
    }

    @Override
    public boolean add(Report report) throws ServiceException {
        boolean result;
        try {
            result = reportDAO.add(report);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public Report getById(long id) throws ServiceException {
        Report report;
        try {
            report = reportDAO.getById(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return report;
    }

    @Override
    public List<Report> getAll() throws ServiceException {
        List<Report> reports;
        try {
            reports = reportDAO.getAll();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return reports;
    }

    @Override
    public boolean update(Report report) throws ServiceException {
        boolean result;
        try {
            result = reportDAO.update(report);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public boolean delete(long id) throws ServiceException {
        boolean result;
        try {
            result = reportDAO.delete(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public boolean setEventForReport(long eventId, long reportId) throws ServiceException {
        boolean result;
        try {
            result = reportDAO.setEventForReport(eventId, reportId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public List<Report> getReportsFromEvent(long eventId) throws ServiceException {
        List<Report> reports;
        try {
            reports = reportDAO.getReportsFromEvent(eventId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return reports;
    }

    @Override
    public boolean setReportForSpeaker(long userId, long reportId) throws ServiceException {
        boolean result;
        try {
            result = reportDAO.setEventForReport(userId, reportId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public List<Report> getReportsFromSpeaker(long userId) throws ServiceException {
        List<Report> reports;
        try {
            reports = reportDAO.getReportsFromSpeaker(userId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return reports;
    }

    @Override
    public boolean approveReport(long reportId) throws ServiceException {
        boolean result;
        try {
            Report report = reportDAO.getById(reportId);
            report.setApproved(true);
            result = reportDAO.update(report);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public boolean acceptReport(long reportId) throws ServiceException {
        boolean result;
        try {
            Report report = reportDAO.getById(reportId);
            report.setAccepted(true);
            result = reportDAO.update(report);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return result;
    }
}