package ua.java.conferences.entities;

import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private long id;

    private String email;

    private transient String password;

    private String name;

    private String surname;

    private boolean emailNotification;

    private int roleId;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return this.surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public boolean isEmailNotification() {
        return this.emailNotification;
    }

    public void setEmailNotification(boolean emailNotification) {
        this.emailNotification = emailNotification;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", emailNotification=" + emailNotification +
                ", roleId=" + roleId +
                '}';
    }

    public static final class UserBuilder {

        private final User user;

        public UserBuilder() {
            this.user = new User();
        }

        public UserBuilder setId(long id) {
            user.setId(id);
            return this;
        }

        public UserBuilder setEmail(String email) {
            user.setEmail(email);
            return this;
        }

        public UserBuilder setPassword(String password) {
            user.setPassword(password);
            return this;
        }

        public UserBuilder setName(String name) {
            user.setName(name);
            return this;
        }

        public UserBuilder setSurname(String surname) {
            user.setSurname(surname);
            return this;
        }

        public UserBuilder setEmailNotification(boolean b) {
            user.setEmailNotification(b);
            return this;
        }

        public UserBuilder setRoleId(int roleId) {
            user.setRoleId(roleId);
            return this;
        }

        public User get() {
            return user;
        }
    }
}