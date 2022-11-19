package ua.java.conferences.dao.mysql;

import ua.java.conferences.connection.DataSource;
import ua.java.conferences.dao.EventDAO;
import ua.java.conferences.entity.*;
import ua.java.conferences.entity.builder.EventBuilder;
import ua.java.conferences.exception.DAOException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static ua.java.conferences.dao.mysql.constants.EventConstants.*;
import static ua.java.conferences.dao.mysql.constants.SQLFields.*;

public class MysqlEventDAO implements EventDAO {

    @Override
    public boolean add(Event event) throws DAOException {
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
            throw new DAOException(e);
        }
        return true;
    }

    @Override
    public Event getById(long id) throws DAOException {
        return getEvent(id, GET_EVENT_BY_ID);
    }

    private Event getEvent(long id, String getEventById) throws DAOException {
        Event event = null;
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getEventById)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    event = createEvent(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return event;
    }

    @Override
    public Event getByTitle(String title) throws DAOException {
        Event event = null;
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_EVENT_BY_TITLE)) {
            preparedStatement.setString(1, title);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    event = createEvent(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return event;
    }

    @Override
    public List<Event> getAll() throws DAOException {
        return getEvents(GET_EVENTS, 0);
    }

    @Override
    public boolean update(Event event) throws DAOException {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(EDIT_EVENT)) {
            setEventFields(event, preparedStatement);
            preparedStatement.setLong(5, event.getId());
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
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_EVENT)) {
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
    public boolean setVisitorsCount(long eventId, int visitorsCount) throws DAOException {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SET_VISITORS)) {
            preparedStatement.setInt(1, visitorsCount);
            preparedStatement.setLong(2, eventId);
            if (executeStatement(preparedStatement)) {
                return false;
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return true;
    }

    @Override
    public List<Event> getEventsByUser(long userId) throws DAOException {
        return getEvents(GET_USERS_EVENTS, userId);
    }

    @Override
    public List<Event> getEventsBySpeaker(long userId) throws DAOException {
        return getEvents(GET_SPEAKERS_EVENTS, userId);
    }

    @Override
    public Event getEventByReport(long reportId) throws DAOException {
        return getEvent(reportId, GET_REPORT_EVENT);
    }

    private void setEventFields(Event event, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, event.getTitle());
        preparedStatement.setDate(2, Date.valueOf(event.getDate()));
        preparedStatement.setString(3, event.getLocation());
        preparedStatement.setString(4, event.getDescription());
    }

    private List<Event> getEvents(String query, long id) throws DAOException {
        List<Event> events = new ArrayList<>();
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            if (id > 0) {
                preparedStatement.setLong(1, id);
            }
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()){
                    events.add(createEvent(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
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