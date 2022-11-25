package ua.java.conferences.dto.response;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserResponseDTO that = (UserResponseDTO) o;
        return id == that.id && notification == that.notification && email.equals(that.email) && name.equals(that.name) && surname.equals(that.surname) && role.equals(that.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, name, surname, notification, role);
    }
}