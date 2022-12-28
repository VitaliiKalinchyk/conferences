package ua.java.conferences.model.entities;

import lombok.*;
import java.io.Serializable;

@Data
@Builder
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private long id;
    private String email;
    private transient String password;
    private String name;
    private String surname;
    @EqualsAndHashCode.Exclude private int roleId;
}