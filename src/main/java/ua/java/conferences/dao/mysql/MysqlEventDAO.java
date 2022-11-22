package ua.java.conferences.dao.mysql;

import ua.java.conferences.connection.DataSource;
import ua.java.conferences.dao.EventDAO;
import ua.java.conferences.entities.*;
import ua.java.conferences.exceptions.DAOException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static ua.java.conferences.dao.mysql.constants.EventConstants.*;
import static ua.java.conferences.dao.mysql.constants.SQLFields.*;

public class MysqlEventDAO implements EventDAO {

    @Override
    public void add(Event event) throws DAOException {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_EVENT)) {
            setStatementFields(event, preparedStatement);
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
            preparedStatement.setLong(1, id);
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
            preparedStatement.setString(1, title);
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
    public List<Event> getEventsBySpeaker(long speakerId) throws DAOException {
        return getUsersEvents(speakerId, GET_SPEAKERS_EVENTS);
    }

    @Override
    public void update(Event event) throws DAOException {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(EDIT_EVENT)) {
            setStatementFields(event, preparedStatement);
            preparedStatement.setLong(5, event.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void delete(long id) throws DAOException {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_EVENT)) {
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void setVisitorsCount(long eventId, int visitorsCount) throws DAOException {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SET_VISITORS)) {
            preparedStatement.setInt(1, visitorsCount);
            preparedStatement.setLong(2, eventId);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    private void setStatementFields(Event event, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, event.getTitle());
        preparedStatement.setDate(2, Date.valueOf(event.getDate()));
        preparedStatement.setString(3, event.getLocation());
        preparedStatement.setString(4, event.getDescription());
    }

    private List<Event> getUsersEvents(long userId, String query) throws DAOException {
        List<Event> events = new ArrayList<>();
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, userId);
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