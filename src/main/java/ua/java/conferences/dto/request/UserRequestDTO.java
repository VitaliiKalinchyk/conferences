package ua.java.conferences.dto.request;

public class UserRequestDTO {

    public final long id;

    public final String email;

    public final String password;

    public final String name;

    public final String surname;

    public final boolean notification;

    public UserRequestDTO(long id, String email, String password, String name, String surname, boolean notification) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.notification = notification;
    }
}