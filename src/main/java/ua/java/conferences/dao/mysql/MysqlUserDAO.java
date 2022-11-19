package ua.java.conferences.dao.mysql;

import ua.java.conferences.connection.DataSource;
import ua.java.conferences.dao.UserDAO;
import ua.java.conferences.entities.*;
import ua.java.conferences.entities.role.Role;
import ua.java.conferences.exceptions.DAOException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static ua.java.conferences.dao.mysql.constants.SQLFields.*;
import static ua.java.conferences.dao.mysql.constants.UserConstants.*;

public class MysqlUserDAO implements UserDAO {

    @Override
    public boolean add(User user) throws DAOException {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_USER, RETURN_GENERATED_KEYS)) {
            setFields(user, preparedStatement);
            if (executeStatement(preparedStatement)) {
                return false;
            }
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return true;
    }

    @Override
    public User getById(long userId) throws DAOException {
        return getUser(userId, GET_USER_BY_ID);
    }

    private User getUser(long userId, String getUserById) throws DAOException {
        User user = null;
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getUserById)) {
            preparedStatement.setLong(1, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    user = createUser(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return user;
    }

    @Override
    public User getByEmail(String email) throws DAOException {
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
        return user;
    }

    @Override
    public List<User> getAll() throws DAOException {
        return this.getUsers(GET_USERS, 0);
    }

    @Override
    public boolean update(User user) throws DAOException {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(EDIT_USER)) {
            setFields(user, preparedStatement);
            preparedStatement.setLong(6, user.getId());
            if (executeStatement(preparedStatement)) {
                return false;
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return true;
    }

    @Override
    public boolean delete(long userId) throws DAOException {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER)) {
            preparedStatement.setLong(1, userId);
            if (executeStatement(preparedStatement)) {
                return false;
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return true;
    }

    @Override
    public boolean registerForEvent(long userId, long eventId) throws DAOException {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(REGISTER_FOR_EVENT)) {
            preparedStatement.setLong(1, userId);
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
    public Role getUsersRole(long userId) throws DAOException {
        Role role = null;
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ROLE)) {
            preparedStatement.setLong(1, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    role = Role.valueOf(resultSet.getString(ROLE));
                }
            }
        }catch (SQLException e) {
                throw new DAOException(e);
        }
        return role;
    }

    @Override
    public boolean setUsersRole(long userId, Role role) throws DAOException {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SET_ROLE)) {
            preparedStatement.setInt(1, role.getValue());
            preparedStatement.setLong(2, userId);
            if (executeStatement(preparedStatement)) {
                return false;
            }
        }catch (SQLException e) {
            throw new DAOException(e);
        }
        return true;
    }

    @Override
    public List<User> getUsersByRole(Role role) throws DAOException {
        return this.getUsers(GET_USERS_BY_ROLE, role.getValue());
    }

    @Override
    public List<User> getUsersByEvent(long eventId) throws DAOException {
        return this.getUsers(GET_USERS_BY_EVENT, eventId);
    }

    @Override
    public User getSpeakerByReport(long reportId) throws DAOException {
        return getUser(reportId, GET_SPEAKER);
    }

    private List<User> getUsers(String query, long id) throws DAOException {
        List<User> users = new ArrayList<>();
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            if (id > 0) {
                preparedStatement.setLong(1, id);
            }
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

    private User createUser(ResultSet resultSet) throws SQLException {
        return new User.UserBuilder()
                .setId(resultSet.getInt(ID))
                .setEmail(resultSet.getString(EMAIL))
                .setName(resultSet.getString(NAME))
                .setSurname(resultSet.getString(SURNAME))
                .setPassword(resultSet.getString(PASSWORD))
                .setEmailNotification(resultSet.getInt(NOTIFICATION) == 1)
                .get();
    }

    private void setFields(User user, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, user.getEmail());
        preparedStatement.setString(2, user.getPassword());
        preparedStatement.setString(3, user.getName());
        preparedStatement.setString(4, user.getSurname());
        preparedStatement.setInt(5, user.isEmailNotification() ? 1 : 0);
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