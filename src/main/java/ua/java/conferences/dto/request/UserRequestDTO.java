package ua.java.conferences.dto.request;

import java.io.Serializable;
import java.util.Objects;

public class UserRequestDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private long id;

    private String email;

    private String password;

    private String name;

    private String surname;

    private boolean notification;

    public UserRequestDTO(long id, String email, String name, String surname, boolean notification) {
        setId(id);
        setEmail(email);
        setName(name);
        setSurname(surname);
        setNotification(notification);
    }

    public UserRequestDTO(String email, String password, String name, String surname, boolean notification) {
        setEmail(email);
        setPassword(password);
        setName(name);
        setSurname(surname);
        setNotification(notification);
    }

    public UserRequestDTO(long id, String email, String password, String name, String surname, boolean notification) {
        setId(id);
        setEmail(email);
        setPassword(password);
        setName(name);
        setSurname(surname);
        setNotification(notification);
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

    @Override
    public String toString() {
        return "UserRequestDTO{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", notification=" + notification +
                '}';
    }
}