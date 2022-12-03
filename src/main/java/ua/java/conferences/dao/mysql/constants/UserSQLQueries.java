package ua.java.conferences.dao.mysql.constants;

public final class UserSQLQueries {

    private UserSQLQueries() {}

    public static final String ADD_USER =
            "INSERT INTO user (email, password, name, surname, notification) VALUES (?,?,?,?,?)";

    public static final String GET_USER_BY_ID = "SELECT * FROM user WHERE id=?";

    public static final String GET_USER_BY_EMAIL = "SELECT * FROM user WHERE email=?";

    public static final String GET_USERS = "SELECT * FROM user";

    public static final String GET_SPEAKERS = "SELECT * FROM user WHERE role_id = 3";

    public static final String EDIT_USER = "UPDATE user SET email=?, name=?, surname=?, notification=? WHERE id=?";

    public static final String EDIT_EMAIL = "UPDATE user SET email=? WHERE id=?";

    public static final String EDIT_PASSWORD = "UPDATE user SET password=? WHERE id=?";

    public static final String DELETE_USER = "DELETE FROM user WHERE id=?";

    public static final String REGISTER_FOR_EVENT = "INSERT INTO user_has_event VALUES (?,?)";

    public static final String SET_ROLE = "UPDATE user SET role_id=? WHERE id=?";

    public static final String IS_REGISTERED = "SELECT * from user_has_event event where user_id=? AND event_id=?";
}