package ua.java.conferences.model.services.implementation;

import ua.java.conferences.model.dao.ReportDAO;
import ua.java.conferences.dto.ReportDTO;
import ua.java.conferences.model.entities.Report;
import ua.java.conferences.exceptions.*;
import ua.java.conferences.model.services.ReportService;

import java.util.*;

import static ua.java.conferences.exceptions.constants.Message.ENTER_CORRECT_TOPIC;
import static ua.java.conferences.model.utils.ConvertorUtil.*;
import static ua.java.conferences.model.utils.ValidatorUtil.*;

public class ReportServiceImpl implements ReportService {
    private final ReportDAO reportDAO;

    public ReportServiceImpl(ReportDAO reportDAO) {
        this.reportDAO = reportDAO;
    }

    @Override
    public void addReport(ReportDTO reportDTO) throws ServiceException {
        validateComplexName(reportDTO.getTopic(), ENTER_CORRECT_TOPIC);
        Report report = convertDTOToReport(reportDTO);
        try {
            reportDAO.add(report);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public ReportDTO getById(String reportIdString) throws ServiceException {
        ReportDTO reportDTO;
        long reportId = getReportId(reportIdString);
        try {
            Report report = reportDAO.getById(reportId).orElseThrow(NoSuchReportException::new);
            reportDTO = convertReportToDTO(report);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return reportDTO;
    }

    @Override
    public List<ReportDTO> getAll() throws ServiceException {
        List<ReportDTO> reportDTOS = new ArrayList<>();
        try {
            List<Report> reports = reportDAO.getAll();
            reports.forEach(report -> reportDTOS.add(convertReportToDTO(report)));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return reportDTOS;
    }

    @Override
    public List<ReportDTO> viewEventsReports(String eventIdString) throws ServiceException {
        List<ReportDTO> reportDTOS = new ArrayList<>();
        try {
            long eventId = getLong(eventIdString);
            List<Report> reports = reportDAO.getEventsReports(eventId);
            reports.forEach(report -> reportDTOS.add(convertReportToDTO(report)));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return reportDTOS;
    }

    @Override
    public List<ReportDTO> viewSpeakersReports(long speakerId) throws ServiceException {
        List<ReportDTO> reportDTOS = new ArrayList<>();
        try {
            List<Report> reports = reportDAO.getSpeakersReports(speakerId);
            reports.forEach(report -> reportDTOS.add(convertReportToDTO(report)));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return reportDTOS;
    }

    @Override
    public void update(ReportDTO reportDTO) throws ServiceException {
        validateComplexName(reportDTO.getTopic(), ENTER_CORRECT_TOPIC);
        Report report = convertDTOToReport(reportDTO);
        try {
            reportDAO.update(report);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void setSpeaker(long reportId, long speakerId) throws ServiceException {
        try {
            reportDAO.setSpeaker(reportId, speakerId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteSpeaker(long reportId) throws ServiceException {
        try {
            reportDAO.deleteSpeaker(reportId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void delete(String reportIdString) throws ServiceException {
        long reportId = getReportId(reportIdString);
        try {
            reportDAO.delete(reportId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}