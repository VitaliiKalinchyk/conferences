package ua.java.conferences.model.services;

import ua.java.conferences.dto.ReportDTO;
import ua.java.conferences.exceptions.*;

import java.util.List;

public interface ReportService extends Service<ReportDTO> {

    void addReport(ReportDTO reportDTO) throws ServiceException;

    List<ReportDTO> viewEventsReports(String eventIdString) throws ServiceException;

    List<ReportDTO> viewSpeakersReports(long speakerId) throws ServiceException;

    void setSpeaker(long reportIdString, long speakerId) throws ServiceException;

    void deleteSpeaker(long reportId) throws ServiceException;
}