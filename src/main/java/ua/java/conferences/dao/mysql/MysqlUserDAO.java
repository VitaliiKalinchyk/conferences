package ua.java.conferences.dao.mysql;

import ua.java.conferences.connection.DataSource;
import ua.java.conferences.dao.*;
import ua.java.conferences.entities.*;
import ua.java.conferences.entities.role.Role;
import ua.java.conferences.exceptions.DAOException;
import ua.java.conferences.utils.sorting.Sorting;

import java.sql.*;
import java.util.*;

import static ua.java.conferences.dao.mysql.constants.SQLFields.*;
import static ua.java.conferences.dao.mysql.constants.UserSQLQueries.*;

public class MysqlUserDAO implements UserDAO {

    @Override
    public void add(User user) throws DAOException {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_USER)) {
            setStatementFieldsForAddMethod(user, preparedStatement);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public Optional<User> getById(long userId) throws DAOException {
        User user = null;
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_BY_ID)) {
            int k = 0;
            preparedStatement.setLong(++k, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    user = createUser(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return  Optional.ofNullable(user);
    }

    @Override
    public Optional<User>  getByEmail(String email) throws DAOException {
        User user = null;
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_BY_EMAIL)) {
            int k = 0;
            preparedStatement.setString(++k, email);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    user = createUser(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return  Optional.ofNullable(user);
    }

    @Override
    public List<User> getAll() throws DAOException {
        List<User> users = new ArrayList<>();
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_USERS)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    users.add(createUser(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return users;
    }

    @Override
    public List<User> getSorted(Sorting sorting, int offset, int records) throws DAOException {
        List<User> users = new ArrayList<>();
        String query = String.format(GET_SORTED, sorting.getFilter(), sorting.getSort(), sorting.getOrder());
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            int k = 0;
            preparedStatement.setInt(++k, offset);
            preparedStatement.setInt(++k, records);
            preparedStatement.execute();
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    users.add(createUser(resultSet));
                }
            }
        }catch (SQLException e) {
            throw new DAOException(e);
        }
        return users;
    }

    @Override
    public int getNumberOfRecords (Sorting sorting) throws DAOException {
        int numberOfRecords = 0;
        String query = String.format(GET_NUMBER_OF_RECORDS, sorting.getFilter());
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    numberOfRecords = resultSet.getInt(NUMBER_OF_RECORDS);
                }
            }
        }catch (SQLException e) {
            throw new DAOException(e);
        }
        return numberOfRecords;
    }

    @Override
    public void update(User user) throws DAOException {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER)) {
            setStatementFieldsForUpdateMethod(user, preparedStatement);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void updatePassword(User user) throws DAOException {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PASSWORD)) {
            int k = 0;
            preparedStatement.setString(++k, user.getPassword());
            preparedStatement.setLong(++k, user.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void delete(long userId) throws DAOException {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER)) {
            int k = 0;
            preparedStatement.setLong(++k, userId);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void registerForEvent(long userId, long eventId) throws DAOException {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(REGISTER_FOR_EVENT)) {
            setIds(userId, eventId, preparedStatement);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void cancelRegistration(long userId, long eventId) throws DAOException {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CANCEL_REGISTRATION)) {
            setIds(userId, eventId, preparedStatement);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void setUserRole(String userEmail, Role role) throws DAOException {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SET_ROLE)) {
            int k = 0;
            preparedStatement.setInt(++k, role.getValue());
            preparedStatement.setString(++k, userEmail);
            preparedStatement.execute();
        }catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public boolean isRegistered(long userId, long eventId) throws DAOException {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(IS_REGISTERED)) {
            setIds(userId, eventId, preparedStatement);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return true;
                }
            }
        }catch (SQLException e) {
            throw new DAOException(e);
        }
        return false;
    }

    private User createUser(ResultSet resultSet) throws SQLException {
        return new User.Builder()
                .setId(resultSet.getInt(ID))
                .setEmail(resultSet.getString(EMAIL))
                .setName(resultSet.getString(NAME))
                .setSurname(resultSet.getString(SURNAME))
                .setPassword(resultSet.getString(PASSWORD))
                .setEmailNotification(resultSet.getInt(NOTIFICATION) == 1)
                .setRoleId(resultSet.getInt(ROLE_ID))
                .get();
    }

    private void setStatementFieldsForAddMethod(User user, PreparedStatement preparedStatement) throws SQLException {
        int k = 0;
        preparedStatement.setString(++k, user.getEmail());
        preparedStatement.setString(++k, user.getPassword());
        preparedStatement.setString(++k, user.getName());
        preparedStatement.setString(++k, user.getSurname());
        preparedStatement.setInt(++k, user.isEmailNotification() ? 1 : 0);
    }

    private static void setStatementFieldsForUpdateMethod(User user, PreparedStatement preparedStatement)
            throws SQLException {
        int k = 0;
        preparedStatement.setString(++k, user.getEmail());
        preparedStatement.setString(++k, user.getName());
        preparedStatement.setString(++k, user.getSurname());
        preparedStatement.setInt(++k, user.isEmailNotification() ? 1 : 0);
        preparedStatement.setLong(++k, user.getId());
    }

    private static void setIds(long userId, long eventId, PreparedStatement preparedStatement) throws SQLException {
        int k = 0;
        preparedStatement.setLong(++k, userId);
        preparedStatement.setLong(++k, eventId);
    }
}