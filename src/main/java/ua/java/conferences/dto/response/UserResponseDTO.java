package ua.java.conferences.dto.response;

public class UserResponseDTO {

    public final long id;

    public final String email;

    public final String name;

    public final String surname;

    public final boolean notification;

    public final String role;

    public UserResponseDTO(long id, String email, String name, String surname, boolean notification, String role) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.notification = notification;
        this.role = role;
    }
}