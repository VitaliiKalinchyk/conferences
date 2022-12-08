package ua.java.conferences.dao.mysql;

import ua.java.conferences.connection.DataSource;
import ua.java.conferences.dao.EventDAO;
import ua.java.conferences.entities.*;
import ua.java.conferences.exceptions.DAOException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static ua.java.conferences.dao.mysql.constants.EventSQLQueries.*;
import static ua.java.conferences.dao.mysql.constants.SQLFields.*;

public class MysqlEventDAO implements EventDAO {

    @Override
    public void add(Event event) throws DAOException {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_EVENT)) {
            int k = 0;
            setStatementFields(event, preparedStatement, k);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public Optional<Event> getById(long id) throws DAOException {
        Event event = null;
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_EVENT_BY_ID)) {
            int k = 0;
            preparedStatement.setLong(++k, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    event = createEvent(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return Optional.ofNullable(event);
    }

    @Override
    public Optional<Event> getByTitle(String title) throws DAOException {
        Event event = null;
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_EVENT_BY_TITLE)) {
            int k = 0;
            preparedStatement.setString(++k, title);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    event = createEvent(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return Optional.ofNullable(event);
    }

    @Override
    public List<Event> getAll() throws DAOException {
        return getEvents(GET_EVENTS);
    }


    @Override
    public List<Event> getSortedEvents(String filter, String sortField, String order) throws DAOException {
        String query = String.format(GET_SORTED_EVENTS, filter, sortField, order);
        return getEvents(query);
    }

    @Override
    public List<Event> getEventsByVisitor(long userId) throws DAOException {
        return getUsersEvents(userId, GET_VISITORS_EVENTS);
    }

    @Override
    public List<Event> getPastEventsByVisitor(long userId) throws DAOException {
        return getUsersEvents(userId, GET_PAST_VISITORS_EVENTS);
    }

    @Override
    public List<Event> getEventsBySpeaker(long speakerId) throws DAOException {
        return getUsersEvents(speakerId, GET_SPEAKERS_EVENTS);
    }

    @Override
    public void update(Event event) throws DAOException {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(EDIT_EVENT)) {
            int k = 0;
            k = setStatementFields(event, preparedStatement, k);
            preparedStatement.setLong(++k, event.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void delete(long id) throws DAOException {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_EVENT)) {
            int k = 0;
            preparedStatement.setLong(++k, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void setVisitorsCount(long eventId, int visitorsCount) throws DAOException {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SET_VISITORS)) {
            int k = 0;
            preparedStatement.setInt(++k, visitorsCount);
            preparedStatement.setLong(++k, eventId);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    private int setStatementFields(Event event, PreparedStatement preparedStatement, int k) throws SQLException {
        preparedStatement.setString(++k, event.getTitle());
        preparedStatement.setDate(++k, Date.valueOf(event.getDate()));
        preparedStatement.setString(++k, event.getLocation());
        preparedStatement.setString(++k, event.getDescription());
        return k;
    }

    private List<Event> getUsersEvents(long userId, String query) throws DAOException {
        List<Event> events = new ArrayList<>();
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            int k = 0;
            preparedStatement.setLong(++k, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    events.add(createUserEvent(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return events;
    }

    private List<Event> getEvents(String getEvents) throws DAOException {
        List<Event> events = new ArrayList<>();
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getEvents)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    events.add(createEvent(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return events;
    }

    private Event createEvent(ResultSet resultSet) throws SQLException {
        return new Event.EventBuilder()
                .setId(resultSet.getLong(ID))
                .setTitle(resultSet.getString(TITLE))
                .setDate(resultSet.getDate(DATE).toLocalDate())
                .setLocation(resultSet.getString(LOCATION))
                .setDescription(resultSet.getString(DESCRIPTION))
                .setRegistrations(resultSet.getInt(REGISTRATIONS))
                .setVisitors(resultSet.getInt(VISITORS))
                .setReports(resultSet.getInt(REPORTS))
                .get();
    }

    private Event createUserEvent(ResultSet resultSet) throws SQLException {
        return new Event.EventBuilder()
                .setId(resultSet.getLong(ID))
                .setTitle(resultSet.getString(TITLE))
                .setDate(resultSet.getDate(DATE).toLocalDate())
                .setLocation(resultSet.getString(LOCATION))
                .setDescription(resultSet.getString(DESCRIPTION))
                .get();
    }
}