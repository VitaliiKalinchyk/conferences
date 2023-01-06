package ua.java.conferences.model.entities.role;

/**
 * Role entity enum. Matches table 'role' in database.
 *
 * @author Vitalii Kalinchyk
 * @version 1.0
 */
public enum Role {ADMIN(1), MODERATOR(2), SPEAKER(3), VISITOR(4);
    private final int value;
    
    Role(int value) {
        this.value = value;
    }

    /**
     * @return the value assigned to this Role
     */
    public int getValue() {
        return this.value;
    }

    /**
     * Obtains the role by the value. Visitor by default.
     * @param value matching role
     * @return the role assigned to this value
     */
    public static Role getRole(int value) {
        for (Role role: Role.values()) {
            if (role.value == value) {
                return role;
            }
        }
        return VISITOR;
    }
}