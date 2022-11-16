package ua.java.conferences.dao.mysql;

import ua.java.conferences.connection.DataSource;
import ua.java.conferences.dao.ReportDAO;
import ua.java.conferences.entity.*;
import ua.java.conferences.entity.builder.ReportBuilder;
import ua.java.conferences.exception.DAOException;

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
    public Report getById(int id) throws DAOException {
        Report report;
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_REPORT_BY_ID)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    report = createReport(resultSet);
                } else {
                    throw new DAOException("No such report");
                }
            }        } catch (SQLException e) {
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
            preparedStatement.setInt(4, report.getId());
            if (executeStatement(preparedStatement)) {
                return false;
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return true;
    }

    @Override
    public boolean delete(Report report) throws DAOException {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_REPORT)) {
            preparedStatement.setInt(1, report.getId());
            if (executeStatement(preparedStatement)) {
                return false;
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return true;
    }

    @Override
    public boolean setEventForReport(Event event, Report report) throws DAOException {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SET_EVENT)) {
            if (getReportsFromId(report, preparedStatement, event.getId())) {
                return false;
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return true;
    }

    @Override
    public List<Report> getReportsFromEvent(Event event) throws DAOException {
        return getReports(GET_EVENTS_REPORT, event.getId());
    }

    @Override
    public boolean setReportForSpeaker(User speaker, Report report) throws DAOException {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SET_REPORT)) {
            if (getReportsFromId(report, preparedStatement, speaker.getId())) {
                return false;
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return true;
    }

    @Override
    public List<Report> getReportsFromSpeaker(User speaker) throws DAOException {
        return getReports(GET_SPEAKERS_REPORT, speaker.getId());
    }

    private List<Report> getReports(String query, int id) throws DAOException {
        List<Report> reports = new ArrayList<>();
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            if (id > 0) {
                preparedStatement.setInt(1, id);
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

    private boolean getReportsFromId(Report report, PreparedStatement preparedStatement, int id) throws SQLException {
        preparedStatement.setInt(1, id);
        preparedStatement.setInt(2, report.getId());
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
