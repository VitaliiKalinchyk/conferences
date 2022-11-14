package ua.java.conferences.entity.role;

public enum Role {
    ADMIN(1),
    MODERATOR(2),
    SPEAKER(3),
    VISITOR(4);

    private final int value;

    Role(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}