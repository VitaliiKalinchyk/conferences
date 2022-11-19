package ua.java.conferences.dao.mysql;

import ua.java.conferences.connection.DataSource;
import ua.java.conferences.dao.ReportDAO;
import ua.java.conferences.entities.*;
import ua.java.conferences.entities.builders.ReportBuilder;
import ua.java.conferences.exceptions.DAOException;

import java.sql.*;
import java.util.*;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static ua.java.conferences.dao.mysql.constants.ReportConstants.*;
import static ua.java.conferences.dao.mysql.constants.SQLFields.*;

public class MysqlReportDAO implements ReportDAO {

    @Override
    public boolean add(Report report) throws DAOException {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_REPORT, RETURN_GENERATED_KEYS)) {
            setReportFields(report, preparedStatement);
            if (executeStatement(preparedStatement)) {
                return false;
            }
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    report.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return true;
    }

    @Override
    public Report getById(long id) throws DAOException {
        Report report = null;
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_REPORT_BY_ID)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    report = createReport(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return report;
    }

    @Override
    public List<Report> getAll() throws DAOException {
        return this.getReports(GET_REPORTS, 0);
    }

    @Override
    public boolean update(Report report) throws DAOException {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(EDIT_REPORT)) {
            setReportFields(report, preparedStatement);
            preparedStatement.setLong(4, report.getId());
            if (executeStatement(preparedStatement)) {
                return false;
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return true;
    }

    @Override
    public boolean delete(long id) throws DAOException {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_REPORT)) {
            preparedStatement.setLong(1, id);
            if (executeStatement(preparedStatement)) {
                return false;
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return true;
    }

    @Override
    public boolean setEventForReport(long eventId, long reportId) throws DAOException {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SET_EVENT)) {
            if (getReportsFromId(reportId, preparedStatement, eventId)) {
                return false;
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return true;
    }

    @Override
    public List<Report> getReportsFromEvent(long eventId) throws DAOException {
        return getReports(GET_EVENTS_REPORT, eventId);
    }

    @Override
    public boolean setReportForSpeaker(long userId, long reportId) throws DAOException {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SET_REPORT)) {
            if (getReportsFromId(reportId, preparedStatement, userId)) {
                return false;
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return true;
    }

    @Override
    public List<Report> getReportsFromSpeaker(long userId) throws DAOException {
        return getReports(GET_SPEAKERS_REPORT, userId);
    }

    private List<Report> getReports(String query, long id) throws DAOException {
        List<Report> reports = new ArrayList<>();
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            if (id > 0) {
                preparedStatement.setLong(1, id);
            }
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    reports.add(createReport(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return reports;
    }

    private Report createReport(ResultSet resultSet) throws SQLException {
        return new ReportBuilder()
                .setId(resultSet.getInt(ID))
                .setTopic(resultSet.getString(TOPIC))
                .setAccepted(resultSet.getInt(ACCEPTED) == 1)
                .setApproved(resultSet.getInt(APPROVED) == 1)
                .get();
    }

    private void setReportFields(Report report, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, report.getTopic());
        preparedStatement.setInt(2, report.isAccepted() ? 1 : 0);
        preparedStatement.setInt(3, report.isApproved() ? 1 : 0);
    }

    private boolean getReportsFromId(long reportId, PreparedStatement preparedStatement, long id) throws SQLException {
        preparedStatement.setLong(1, id);
        preparedStatement.setLong(2, reportId);
        try {
            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows == 0;
        } catch (SQLException var5) {
            return true;
        }
    }

    private boolean executeStatement(PreparedStatement preparedStatement) {
        try {
            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows == 0;
        } catch (SQLException var2) {
            return true;
        }
    }
}