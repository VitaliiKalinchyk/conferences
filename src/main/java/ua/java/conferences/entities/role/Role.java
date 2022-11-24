package ua.java.conferences.entities.role;

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

    public String getName(int value) {
        for (Role role: Role.values()) {
            if (role.value == value) {
                return role.name();
            }
        }
        return VISITOR.name();
    }
}