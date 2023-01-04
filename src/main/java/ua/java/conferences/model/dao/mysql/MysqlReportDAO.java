package ua.java.conferences.model.dao.mysql;

import ua.java.conferences.model.dao.ReportDAO;
import ua.java.conferences.exceptions.DAOException;
import ua.java.conferences.model.entities.*;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;

import static java.sql.Types.NULL;
import static ua.java.conferences.model.dao.mysql.constants.ReportSQLQueries.*;
import static ua.java.conferences.model.dao.mysql.constants.SQLFields.*;

public class MysqlReportDAO implements ReportDAO {
    private final DataSource dataSource;

    public MysqlReportDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(Report report) throws DAOException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_REPORT)) {
            int k = 0;
            preparedStatement.setString(++k, report.getTopic());
            preparedStatement.setLong(++k, report.getEvent().getId());
            User speaker = report.getSpeaker();
            if (speaker != null) {
                preparedStatement.setLong(++k, speaker.getId());
            } else {
                preparedStatement.setNull(++k, NULL);
            }
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public Optional<Report> getById(long id) throws DAOException {
        Report report = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_REPORT_BY_ID)) {
            int k = 0;
            preparedStatement.setLong(++k, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    report = getReport(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return Optional.ofNullable(report);
    }

    @Override
    public List<Report> getAll() throws DAOException {
        List<Report> reports = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_REPORTS)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    reports.add(getReport(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return reports;
    }

    @Override
    public List<Report> getEventsReports(long eventId) throws DAOException {
        return getReports(eventId, GET_EVENTS_REPORTS);
    }

    @Override
    public List<Report> getSpeakersReports(long speakerId) throws DAOException {
        return getReports(speakerId, GET_SPEAKERS_REPORTS);
    }

    private List<Report> getReports(long eventId, String getEventsReports) throws DAOException {
        List<Report> reports = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getEventsReports)) {
            int k = 0;
            preparedStatement.setLong(++k, eventId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    reports.add(getReport(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return reports;
    }

    @Override
    public void update(Report report) throws DAOException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(EDIT_REPORT)) {
            int k = 0;
            preparedStatement.setString(++k, report.getTopic());
            preparedStatement.setLong(++k, report.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void delete(long id) throws DAOException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_REPORT)) {
            int k = 0;
            preparedStatement.setLong(++k, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void setSpeaker(long reportId, long speakerId) throws DAOException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SET_SPEAKER)) {
            int k = 0;
            preparedStatement.setLong(++k, speakerId);
            preparedStatement.setLong(++k, reportId);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void deleteSpeaker(long reportId) throws DAOException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SET_SPEAKER)) {
            int k = 0;
            preparedStatement.setNull(++k, NULL);
            preparedStatement.setLong(++k, reportId);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    private Report getReport(ResultSet resultSet) throws SQLException {
        Event event = getEvent(resultSet);
        User speaker = getSpeaker(resultSet);
        return Report.builder()
                .id(resultSet.getLong(ID))
                .topic(resultSet.getString(TOPIC))
                .event(event)
                .speaker(speaker)
                .build();
    }

    private Event getEvent(ResultSet resultSet) throws SQLException {
        Event event = null;
        long eventId = resultSet.getLong(EVENT_ID);
        if (eventId !=0) {
            event = Event.builder()
                    .id(eventId)
                    .title(resultSet.getString(TITLE))
                    .date(LocalDate.parse(resultSet.getString(DATE)))
                    .location(resultSet.getString(LOCATION))
                    .build();
        }
        return event;
    }

    private static User getSpeaker(ResultSet resultSet) throws SQLException {
        User speaker = null;
        long userId = resultSet.getLong(USER_ID);
        if (userId !=0) {
            speaker = User.builder()
                    .id(userId)
                    .email(resultSet.getString(EMAIL))
                    .name(resultSet.getString(NAME))
                    .surname(resultSet.getString(SURNAME))
                    .build();
        }
        return speaker;
    }
}