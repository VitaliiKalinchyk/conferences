package ua.java.conferences.dto.request;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRequestDTO that = (UserRequestDTO) o;
        return id == that.id && notification == that.notification && email.equals(that.email)
                && password.equals(that.password) && name.equals(that.name) && surname.equals(that.surname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, password, name, surname, notification);
    }
}