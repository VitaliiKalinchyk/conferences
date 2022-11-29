package ua.java.conferences.dto.response;

import java.util.Objects;

public class UserResponseDTO {

    private long id;

    private String email;

    private String name;

    private String surname;

    private boolean notification;

    private String role;

    public UserResponseDTO(long id, String email, String name, String surname, boolean notification, String role) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.notification = notification;
        this.role = role;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public boolean isNotification() {
        return notification;
    }

    public void setNotification(boolean notification) {
        this.notification = notification;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserResponseDTO that = (UserResponseDTO) o;
        return id == that.id && notification == that.notification && email.equals(that.email)
                && name.equals(that.name) && surname.equals(that.surname) && role.equals(that.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, name, surname, notification, role);
    }

    @Override
    public String toString() {
        return "UserResponseDTO{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", notification=" + notification +
                ", role='" + role + '\'' +
                '}';
    }
}