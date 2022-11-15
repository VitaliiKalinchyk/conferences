package ua.java.conferences.dao.mysql;

import ua.java.conferences.connection.DataSource;
import ua.java.conferences.dao.EventDAO;
import ua.java.conferences.entity.*;
import ua.java.conferences.entity.builder.EventBuilder;
import ua.java.conferences.exception.DBException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static ua.java.conferences.dao.mysql.constants.EventConstants.*;
import static ua.java.conferences.dao.mysql.constants.SQLFields.*;

public class MysqlEventDAO implements EventDAO {

    @Override
    public boolean add(Event event) throws DBException {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_EVENT, RETURN_GENERATED_KEYS)) {
            setEventFields(event, preparedStatement);
            if (executeStatement(preparedStatement)) {
                return false;
            }
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    event.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new DBException(e);
        }
        return true;
    }

    @Override
    public Event get(Event event) throws DBException {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_EVENT)) {
            preparedStatement.setString(1, event.getTitle());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    event = createEvent(resultSet);
                } else {
                    throw new DBException("No such event");
                }
            }
        } catch (SQLException e) {
            throw new DBException(e);
        }
        return event;
    }

    @Override
    public List<Event> getAll() throws DBException {
        return getEvents(GET_EVENTS, 0);
    }

    @Override
    public boolean update(Event event) throws DBException {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(EDIT_EVENT)) {
            setEventFields(event, preparedStatement);
            preparedStatement.setInt(5, event.getId());
            if (executeStatement(preparedStatement)) {
                return false;
            }
        } catch (SQLException e) {
            throw new DBException(e);
        }
        return true;
    }

    @Override
    public boolean delete(Event event) throws DBException {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_EVENT)) {
            preparedStatement.setInt(1, event.getId());
            if (executeStatement(preparedStatement)) {
                return false;
            }
        } catch (SQLException e) {
            throw new DBException(e);
        }
        return true;
    }

    @Override
    public boolean setVisitors(Event event, int visitors) throws DBException {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SET_VISITORS)) {
            preparedStatement.setInt(1, visitors);
            preparedStatement.setInt(2, event.getId());
            if (executeStatement(preparedStatement)) {
                return false;
            }
        } catch (SQLException e) {
            throw new DBException(e);
        }
        return true;
    }

    @Override
    public List<Event> getEventsByUser(User user) throws DBException {
        return getEvents(GET_USERS_EVENTS, user.getId());
    }

    @Override
    public List<Event> getEventsBySpeaker(User user) throws DBException {
        return getEvents(GET_SPEAKERS_EVENTS, user.getId());
    }

    @Override
    public Event getEventByReport(Report report) throws DBException {
        Event event;
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_REPORT_EVENT)) {
            preparedStatement.setInt(1, report.getId());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    event = createEvent(resultSet);
                } else {
                    throw new DBException("No such event");
                }
            }
        } catch (SQLException e) {
            throw new DBException(e);
        }
        return event;
    }

    private void setEventFields(Event event, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, event.getTitle());
        preparedStatement.setDate(2, Date.valueOf(event.getDate()));
        preparedStatement.setString(3, event.getLocation());
        preparedStatement.setString(4, event.getDescription());
    }

    private List<Event> getEvents(String query, int id) throws DBException {
        List<Event> events = new ArrayList<>();
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            if (id > 0) {
                preparedStatement.setInt(1, id);
            }
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()){
                    events.add(createEvent(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new DBException(e);
        }
        return events;
    }

    private Event createEvent(ResultSet resultSet) throws SQLException {
        return new EventBuilder()
                .setId(resultSet.getInt(ID))
                .setTitle(resultSet.getString(TITLE))
                .setDate(resultSet.getDate(DATE).toLocalDate())
                .setLocation(resultSet.getString(LOCATION))
                .setDescription(resultSet.getString(DESCRIPTION))
                .setVisitors(resultSet.getInt(VISITORS))
                .get();
    }

    private boolean executeStatement(PreparedStatement preparedStatement) {
        try {
            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows == 0;
        } catch (SQLException e) {
            return true;
        }
    }
}