package ua.java.conferences.dto;

import java.io.Serializable;
import java.util.Objects;

public class UserDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private long id;
    private String password;
    private String email;
    private String name;
    private String surname;
    private boolean notification;
    private String role;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
        UserDTO that = (UserDTO) o;
        return email.equals(that.email) && name.equals(that.name) && surname.equals(that.surname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, password, email, name, surname, notification, role);
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + getId() +
                ", email='" + getEmail() + '\'' +
                ", password='" + getPassword() + '\'' +
                ", name='" + getName() + '\'' +
                ", surname='" + getSurname() + '\'' +
                ", notification=" + isNotification() +
                ", role='" + getRole() + '\'' +
                '}';
    }

    public static class Builder {
        private final UserDTO user;

        public Builder() {
            this.user = new UserDTO();
        }

        public Builder setId(long id) {
            user.setId(id);
            return this;
        }

        public Builder setEmail(String email) {
            user.setEmail(email);
            return this;
        }

        public Builder setPassword(String password) {
            user.setPassword(password);
            return this;
        }

        public Builder setName(String name) {
            user.setName(name);
            return this;
        }

        public Builder setSurname(String surname) {
            user.setSurname(surname);
            return this;
        }

        public Builder setRole(String role) {
            user.setRole(role);
            return this;
        }

        public Builder setNotification(boolean notification) {
            user.setNotification(notification);
            return this;
        }

        public UserDTO get() {
            return user;
        }
    }
}