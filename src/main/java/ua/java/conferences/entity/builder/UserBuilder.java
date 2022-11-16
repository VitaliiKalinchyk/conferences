package ua.java.conferences.entity.builder;


import ua.java.conferences.entity.User;

public final class UserBuilder {

    private final User user;

    public UserBuilder() {
        this.user = new User();
    }

    public UserBuilder setId(long id) {
        this.user.setId(id);
        return this;
    }

    public UserBuilder setEmail(String email) {
        this.user.setEmail(email);
        return this;
    }

    public UserBuilder setPassword(String password) {
        this.user.setPassword(password);
        return this;
    }

    public UserBuilder setName(String name) {
        this.user.setName(name);
        return this;
    }

    public UserBuilder setSurname(String surname) {
        this.user.setSurname(surname);
        return this;
    }

    public UserBuilder setEmailNotification(boolean b) {
        this.user.setEmailNotification(b);
        return this;
    }

    public User get() {
        return this.user;
    }
}