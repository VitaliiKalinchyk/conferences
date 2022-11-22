package ua.java.conferences.dao.mysql;

import ua.java.conferences.connection.DataSource;
import ua.java.conferences.dao.ReportDAO;
import ua.java.conferences.entities.*;
import ua.java.conferences.exceptions.DAOException;

import java.sql.*;
import java.util.*;

import static java.sql.Types.NULL;
import static ua.java.conferences.dao.mysql.constants.ReportConstants.*;
import static ua.java.conferences.dao.mysql.constants.SQLFields.*;

public class MysqlReportDAO implements ReportDAO {

    @Override
    public void add(Report report) throws DAOException {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_REPORT)) {
            preparedStatement.setString(1, report.getTopic());
            preparedStatement.setLong(2, report.getEventId());
            User speaker = report.getSpeaker();
            if (speaker != null) {
                preparedStatement.setLong(3, speaker.getId());
            } else {
                preparedStatement.setNull(3, NULL);
            }
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public Optional<Report> getById(long id) throws DAOException {
        Report report = null;
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_REPORT_BY_ID)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    report = createReportWithSpeaker(resultSet);
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
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_REPORTS)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    reports.add(createReportWithSpeaker(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return reports;
    }

    @Override
    public List<Report> getEventsReports(long eventId) throws DAOException {
        List<Report> reports = new ArrayList<>();
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_EVENTS_REPORTS)) {
            preparedStatement.setLong(1, eventId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    reports.add(createReportWithSpeaker(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return reports;
    }

    @Override
    public void update(Report report) throws DAOException {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(EDIT_REPORT)) {
            preparedStatement.setString(1, report.getTopic());
            preparedStatement.setLong(2, report.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void delete(long id) throws DAOException {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_REPORT)) {
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void setSpeaker(long reportId, long speakerId) throws DAOException {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SET_SPEAKER)) {
            preparedStatement.setLong(1, speakerId);
            preparedStatement.setLong(2, reportId);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public Map<Report, Event> getSpeakersReports(long speakerId) throws DAOException {
        Map<Report, Event> reports = new HashMap<>();
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_SPEAKERS_REPORTS)) {
            preparedStatement.setLong(1, speakerId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    reports.put(createReport(resultSet), createEvent(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return reports;
    }

    private Report createReportWithSpeaker(ResultSet resultSet) throws SQLException {
        User speaker = getSpeaker(resultSet);
        return new Report.ReportBuilder()
                .setId(resultSet.getInt(ID))
                .setTopic(resultSet.getString(TOPIC))
                .setEventId(resultSet.getLong(EVENT_ID))
                .setSpeaker(speaker)
                .get();
    }

    private static User getSpeaker(ResultSet resultSet) throws SQLException {
        User speaker = null;
        long userId = resultSet.getLong(USER_ID);
        if (userId !=0) {
            speaker = new User.UserBuilder()
                    .setId(userId)
                    .setName(NAME)
                    .setSurname(SURNAME)
                    .get();
        }
        return speaker;
    }

    private Report createReport(ResultSet resultSet) throws SQLException {
        return new Report.ReportBuilder()
                .setId(resultSet.getInt(ID))
                .setTopic(resultSet.getString(TOPIC))
                .get();
    }

    private Event createEvent(ResultSet resultSet) throws SQLException {
        return new Event.EventBuilder()
                .setTitle(resultSet.getString(TITLE))
                .setDate(resultSet.getDate(DATE).toLocalDate())
                .setLocation(resultSet.getString(LOCATION))
                .get();
    }
}