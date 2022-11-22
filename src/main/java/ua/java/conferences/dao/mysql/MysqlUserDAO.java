package ua.java.conferences.dao.mysql;

import ua.java.conferences.connection.DataSource;
import ua.java.conferences.dao.UserDAO;
import ua.java.conferences.entities.*;
import ua.java.conferences.entities.role.Role;
import ua.java.conferences.exceptions.DAOException;

import java.sql.*;
import java.util.*;

import static ua.java.conferences.dao.mysql.constants.SQLFields.*;
import static ua.java.conferences.dao.mysql.constants.UserConstants.*;

public class MysqlUserDAO implements UserDAO {

    @Override
    public void add(User user) throws DAOException {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_USER)) {
            setStatementFields(user, preparedStatement);
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
            preparedStatement.setLong(1, userId);
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
            preparedStatement.setString(1, email);
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
        return getUsers(GET_USERS);
    }

    @Override
    public List<User> getSpeakers() throws DAOException {
        return getUsers(GET_SPEAKERS);
    }

    @Override
    public void update(User user) throws DAOException {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(EDIT_USER)) {
            setStatementFields(user, preparedStatement);
            preparedStatement.setLong(6, user.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void delete(long userId) throws DAOException {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER)) {
            preparedStatement.setLong(1, userId);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void registerForEvent(long userId, long eventId) throws DAOException {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(REGISTER_FOR_EVENT)) {
            preparedStatement.setLong(1, userId);
            preparedStatement.setLong(2, eventId);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void setUsersRole(long userId, Role role) throws DAOException {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SET_ROLE)) {
            preparedStatement.setInt(1, role.getValue());
            preparedStatement.setLong(2, userId);
            preparedStatement.execute();
        }catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    private User createUser(ResultSet resultSet) throws SQLException {
        return new User.UserBuilder()
                .setId(resultSet.getInt(ID))
                .setEmail(resultSet.getString(EMAIL))
                .setName(resultSet.getString(NAME))
                .setSurname(resultSet.getString(SURNAME))
                .setPassword(resultSet.getString(PASSWORD))
                .setEmailNotification(resultSet.getInt(NOTIFICATION) == 1)
                .setRoleId(resultSet.getInt(ROLE_ID))
                .get();
    }

    private void setStatementFields(User user, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, user.getEmail());
        preparedStatement.setString(2, user.getPassword());
        preparedStatement.setString(3, user.getName());
        preparedStatement.setString(4, user.getSurname());
        preparedStatement.setInt(5, user.isEmailNotification() ? 1 : 0);
    }

    private List<User> getUsers(String query) throws DAOException {
        List<User> users = new ArrayList<>();
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
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
}