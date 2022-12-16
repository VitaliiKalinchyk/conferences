package ua.java.conferences.dao.mysql;

import ua.java.conferences.connection.DataSource;
import ua.java.conferences.dao.EventDAO;
import ua.java.conferences.entities.*;
import ua.java.conferences.entities.role.Role;
import ua.java.conferences.exceptions.DAOException;
import ua.java.conferences.utils.sorting.Sorting;

import java.sql.*;
import java.sql.Date;
import java.util.*;

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
    public List<Event> getSorted(Sorting sorting, int offset, int records) throws DAOException {
        List<Event> events = new ArrayList<>();
        String query = String.format(GET_SORTED, getFilter(sorting.getFilter()), sorting.getSort(), sorting.getOrder());
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            int k = 0;
            preparedStatement.setInt(++k, offset);
            preparedStatement.setInt(++k, records);
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
    public List<Event> getSortedByUser(long userId, Sorting sorting, int offset, int records, String role)
            throws DAOException {
        List<Event> events = new ArrayList<>();
        String query = getQueryForList(sorting, role);
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            int k = 0;
            preparedStatement.setLong(++k, userId);
            preparedStatement.setInt(++k, offset);
            preparedStatement.setInt(++k, records);
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
    public int getNumberOfRecords (long userId, Sorting sorting, String role) throws DAOException {
        int numberOfRecords = 0;
        String query = getRecordsQuery(userId, getFilter(sorting.getFilter()), role);
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            if (userId > 0) {
                setId(userId, preparedStatement);
            }
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

    private String getRecordsQuery(long userId, String filter, String role) {
        if (userId == 0) {
            return String.format(GET_NUMBER_OF_RECORDS, filter);
        } else if (role.equals(Role.SPEAKER.name())) {
            return String.format(GET_NUMBER_OF_RECORDS_BY_SPEAKER, filter);
        }
        return String.format(GET_NUMBER_OF_RECORDS_BY_VISITOR, filter);
    }

    private String getQueryForList(Sorting sorting, String role) {
        String filter = getFilter(sorting.getFilter());
        if (role.equals(Role.SPEAKER.name()))
            return String.format(GET_SPEAKERS_EVENTS, filter, sorting.getSort(), sorting.getOrder());
        return String.format(GET_VISITORS_EVENTS, filter, sorting.getSort(), sorting.getOrder());
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
        return new Event.Builder()
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
        return new Event.Builder()
                .setId(resultSet.getLong(ID))
                .setTitle(resultSet.getString(TITLE))
                .setDate(resultSet.getDate(DATE).toLocalDate())
                .setLocation(resultSet.getString(LOCATION))
                .setDescription(resultSet.getString(DESCRIPTION))
                .get();
    }

    private static String getFilter(String filter) {
        if (filter.equalsIgnoreCase("passed")) {
            return PASSED;
        } else if (filter.equalsIgnoreCase("upcoming")) {
            return UPCOMING;
        }
        return ANY_DATE;
    }
}