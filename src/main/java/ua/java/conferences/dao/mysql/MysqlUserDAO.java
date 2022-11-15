package ua.java.conferences.dao.mysql;

import ua.java.conferences.connection.DataSource;
import ua.java.conferences.dao.UserDAO;
import ua.java.conferences.entity.*;
import ua.java.conferences.entity.builder.UserBuilder;
import ua.java.conferences.entity.role.Role;
import ua.java.conferences.exception.DBException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static ua.java.conferences.dao.mysql.constants.SQLFields.*;
import static ua.java.conferences.dao.mysql.constants.UserConstants.*;

public class MysqlUserDAO implements UserDAO {

    @Override
    public boolean add(User user) throws DBException {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_USER, RETURN_GENERATED_KEYS)) {
            setUsersFields(user, preparedStatement);
            if (executeStatement(preparedStatement)) {
                return false;
            }
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new DBException(e);
        }
        return true;
    }

    @Override
    public User get(User user) throws DBException {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_USER)) {
            preparedStatement.setString(1, user.getEmail());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    user = createUser(resultSet);
                } else {
                throw new DBException("No such user");
                }
            }
        } catch (SQLException e) {
            throw new DBException(e);
        }
        return user;
    }

    @Override
    public List<User> getAll() throws DBException {
        return this.getUsers(GET_USERS, 0);
    }

    @Override
    public boolean update(User user) throws DBException {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(EDIT_USER)) {
            setUsersFields(user, preparedStatement);
            preparedStatement.setInt(6, user.getId());
            if (executeStatement(preparedStatement)) {
                return false;
            }
        } catch (SQLException e) {
            throw new DBException(e);
        }
        return true;
    }

    @Override
    public boolean delete(User user) throws DBException {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER)) {
            preparedStatement.setInt(1, user.getId());
            if (executeStatement(preparedStatement)) {
                return false;
            }
        } catch (SQLException e) {
            throw new DBException(e);
        }
        return true;
    }

    @Override
    public boolean registerForEvent(User user, Event event) throws DBException {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(REGISTER_FOR_EVENT)) {
            preparedStatement.setInt(1, user.getId());
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
    public Role getUsersRole(User user) throws DBException {
        Role role;
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ROLE)) {
            preparedStatement.setInt(1, user.getId());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    role = Role.valueOf(resultSet.getString(ROLE));
                } else {
                    throw new DBException("No such user");
                }
            }
        }catch (SQLException e) {
                throw new DBException(e);
        }
        return role;
    }

    @Override
    public boolean setUsersRole(User user, Role role) throws DBException {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SET_ROLE)) {
            preparedStatement.setInt(1, role.getValue());
            preparedStatement.setInt(2, user.getId());
            if (executeStatement(preparedStatement)) {
                return false;
            }
        }catch (SQLException e) {
            throw new DBException(e);
        }
        return true;
    }

    @Override
    public List<User> getUsersByRole(Role role) throws DBException {
        return this.getUsers(GET_USERS_BY_ROLE, role.getValue());
    }

    @Override
    public List<User> getUsersByEvent(Event event) throws DBException {
        return this.getUsers(GET_USERS_BY_EVENT, event.getId());
    }

    @Override
    public User getSpeakerByReport(Report report) throws DBException {
        User user;
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_SPEAKER)) {
            preparedStatement.setInt(1, report.getId());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    user = createUser(resultSet);
                } else {
                    throw new DBException("No speaker for this report");
                }
            }
        } catch (SQLException e) {
            throw new DBException(e);
        }
        return user;
    }

    private List<User> getUsers(String query, int id) throws DBException {
        List<User> users = new ArrayList<>();
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            if (id > 0) {
                preparedStatement.setInt(1, id);
            }
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    users.add(createUser(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new DBException(e);
        }
        return users;
    }

    private User createUser(ResultSet resultSet) throws SQLException {
        return new UserBuilder()
                .setId(resultSet.getInt(ID))
                .setEmail(resultSet.getString(EMAIL))
                .setName(resultSet.getString(NAME))
                .setSurname(resultSet.getString(SURNAME))
                .setPassword(resultSet.getString(PASSWORD))
                .setEmailNotification(resultSet.getInt(NOTIFICATION) == 1)
                .get();
    }

    private void setUsersFields(User user, PreparedStatement preparedStatement) throws SQLException {
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