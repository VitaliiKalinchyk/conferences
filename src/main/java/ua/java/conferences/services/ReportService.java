package ua.java.conferences.services;

import ua.java.conferences.dto.request.ReportRequestDTO;
import ua.java.conferences.dto.response.*;
import ua.java.conferences.exceptions.*;

import java.util.List;

public interface ReportService extends Service<ReportResponseDTO> {

    void createReport(ReportRequestDTO reportDTO) throws ServiceException;

    List<ReportResponseDTO> viewEventsReports(String eventIdString) throws ServiceException;

    List<ReportResponseDTO> viewSpeakersReports(String speakerIdString) throws ServiceException;

    void updateReport(ReportRequestDTO reportDTO) throws ServiceException;

    void setSpeaker(String reportIdString, String speakerIdString) throws ServiceException;

    void deleteSpeaker(String reportIdString) throws ServiceException;
}