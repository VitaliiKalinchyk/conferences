package ua.java.conferences.dao.mysql.constants;

public final class UserConstants {


    private UserConstants() {}

    public static final String ADD_USER =
            "INSERT INTO user (email, password, name, surname, notification) VALUES (?,?,?,?,?)";

    public static final String GET_USERS = "SELECT * FROM user";

    public static final String GET_USER_BY_ID = "SELECT * FROM user WHERE id=?";

    public static final String GET_USER_BY_EMAIL = "SELECT * FROM user WHERE email=?";


    public static final String EDIT_USER =
            "UPDATE user SET email=?, password=?, name=?, surname=?, notification=? WHERE id=?";

    public static final String DELETE_USER = "DELETE FROM user WHERE id=?";

    public static final String REGISTER_FOR_EVENT = "INSERT INTO user_has_event VALUES (?,?)";

    public static final String GET_ROLE = "SELECT role_name FROM role JOIN user ON role.id=user.role_id WHERE user.id=?";

    public static final String SET_ROLE = "UPDATE user SET role_id=? WHERE id=?";

    public static final String GET_USERS_BY_ROLE = "SELECT * FROM user WHERE role_id=?";

    public static final String GET_USERS_BY_EVENT =
            "SELECT * FROM user JOIN user_has_event ON user.id=user_has_event.user_id WHERE event_id = ?";

    public static final String GET_SPEAKER = "SELECT * FROM user JOIN report ON user.id=report.user_id WHERE report.id=?";
}