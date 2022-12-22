package ua.java.conferences.model.dao.mysql;

import ua.java.conferences.model.connection.DataSource;
import ua.java.conferences.model.dao.EventDAO;
import ua.java.conferences.model.entities.Event;
import ua.java.conferences.model.entities.role.Role;
import ua.java.conferences.exceptions.DAOException;

import java.sql.*;
import java.sql.Date;
import java.util.*;

import static ua.java.conferences.model.dao.mysql.constants.EventSQLQueries.*;
import static ua.java.conferences.model.dao.mysql.constants.SQLFields.*;
import static ua.java.conferences.model.entities.role.Role.SPEAKER;

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
            setId(id, preparedStatement);
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
        List<Event> events = new ArrayList<>();
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_EVENTS)) {
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

    @Override
    public List<Event> getSorted(String query) throws DAOException {
        List<Event> events = new ArrayList<>();
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(String.format(GET_SORTED, query))) {
            preparedStatement.execute();
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

    @Override
    public List<Event> getSortedByUser(String query, Role role) throws DAOException {
        List<Event> events = new ArrayList<>();
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getQueryForList(query, role))) {
            preparedStatement.execute();
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

    @Override
    public void delete(long id) throws DAOException {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_EVENT)) {
            setId(id, preparedStatement);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public int getNumberOfRecords (String filter, Role role) throws DAOException {
        int numberOfRecords = 0;
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getRecordsQuery(filter, role))) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    numberOfRecords = resultSet.getInt(NUMBER_OF_RECORDS);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return numberOfRecords;
    }

    private String getRecordsQuery(String filter, Role role) {
        switch (role) {
            case VISITOR: return String.format(GET_NUMBER_OF_RECORDS_BY_VISITOR, filter);
            case SPEAKER: return String.format(GET_NUMBER_OF_RECORDS_BY_SPEAKER, filter);
            default: return String.format(GET_NUMBER_OF_RECORDS, filter);
        }
    }

    private String getQueryForList(String query, Role role) {
        return role == SPEAKER ? String.format(GET_SPEAKERS_EVENTS, query)
                               : String.format(GET_VISITORS_EVENTS, query);
    }

    private void setId(long id, PreparedStatement preparedStatement) throws SQLException {
        int k = 0;
        preparedStatement.setLong(++k, id);
    }

    private int setStatementFields(Event event, PreparedStatement preparedStatement, int k) throws SQLException {
        preparedStatement.setString(++k, event.getTitle());
        preparedStatement.setDate(++k, Date.valueOf(event.getDate()));
        preparedStatement.setString(++k, event.getLocation());
        preparedStatement.setString(++k, event.getDescription());
        return k;
    }

    private Event createEvent(ResultSet resultSet) throws SQLException {
        return Event.builder()
                .id(resultSet.getLong(ID))
                .title(resultSet.getString(TITLE))
                .date(resultSet.getDate(DATE).toLocalDate())
                .location(resultSet.getString(LOCATION))
                .description(resultSet.getString(DESCRIPTION))
                .registrations(resultSet.getInt(REGISTRATIONS))
                .visitors(resultSet.getInt(VISITORS))
                .reports(resultSet.getInt(REPORTS))
                .build();
    }

    private Event createUserEvent(ResultSet resultSet) throws SQLException {
        return Event.builder()
                .id(resultSet.getLong(ID))
                .title(resultSet.getString(TITLE))
                .date(resultSet.getDate(DATE).toLocalDate())
                .location(resultSet.getString(LOCATION))
                .description(resultSet.getString(DESCRIPTION))
                .build();
    }
}