package ua.java.conferences.services.implementation;

import ua.java.conferences.dao.ReportDAO;
import ua.java.conferences.dto.request.ReportRequestDTO;
import ua.java.conferences.dto.response.*;
import ua.java.conferences.entities.Report;
import ua.java.conferences.exceptions.*;
import ua.java.conferences.services.ReportService;

import java.util.*;

import static ua.java.conferences.exceptions.IncorrectFormatException.Message.ENTER_CORRECT_TOPIC;
import static ua.java.conferences.utils.ConvertorUtil.*;
import static ua.java.conferences.utils.ValidatorUtil.*;

public class ReportServiceImpl implements ReportService {

    private final ReportDAO reportDAO;

    public ReportServiceImpl(ReportDAO reportDAO) {
        this.reportDAO = reportDAO;
    }

    @Override
    public void createReport(ReportRequestDTO reportDTO) throws ServiceException {
        validateReport(reportDTO);
        Report report = convertDTOToReport(reportDTO);
        try {
            reportDAO.add(report);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public ReportResponseDTO view(String reportIdString) throws ServiceException {
        ReportResponseDTO reportDTO;
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
    public List<ReportResponseDTO> viewEventsReports(String eventIdString) throws ServiceException {
        List<ReportResponseDTO> reportDTOS = new ArrayList<>();
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
    public List<ReportResponseDTO> viewSpeakersReports(String speakerIdString) throws ServiceException {
        List<ReportResponseDTO> reportDTOS = new ArrayList<>();
        try {
            long speakerId = getLong(speakerIdString);
            List<Report> reports = reportDAO.getSpeakersReports(speakerId);
            reports.forEach(report -> reportDTOS.add(convertSpeakersReportToDTO(report)));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return reportDTOS;
    }

    @Override
    public void updateReport(ReportRequestDTO reportDTO) throws ServiceException {
        validateReport(reportDTO);
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

    private void validateReport(ReportRequestDTO reportDTO) throws IncorrectFormatException {
        if (!validateComplexName(reportDTO.getTopic())) {
            throw new IncorrectFormatException(ENTER_CORRECT_TOPIC);
        }
    }
}