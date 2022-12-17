package ua.java.conferences.dto;

import lombok.*;
import java.io.Serializable;

@Data
@EqualsAndHashCode(of = {"email", "name", "surname"})
@Builder
public class UserDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private long id;
    private String email;
    private String name;
    private String surname;
    private boolean notification;
    private String role;
}