package ua.java.conferences.services;

import ua.java.conferences.dto.request.ReportRequestDTO;
import ua.java.conferences.dto.response.*;
import ua.java.conferences.exceptions.*;

import java.util.List;

public interface ReportService extends Service<ReportResponseDTO> {

    void createReport(ReportRequestDTO reportDTO) throws ServiceException;

    List<ReportResponseDTO> viewEventsReports(long eventId) throws ServiceException;

    List<ReportResponseDTO> viewSpeakersReports(long speakerId) throws ServiceException;

    void updateReport(ReportRequestDTO reportDTO) throws ServiceException;

    void setSpeaker(long reportId, long speakerId) throws ServiceException;

    void deleteSpeaker(long reportId) throws ServiceException;
}