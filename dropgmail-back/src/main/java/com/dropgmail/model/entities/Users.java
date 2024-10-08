package com.dropgmail.model.entities;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entidad que representa un usuario en la base de datos.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)

    private Integer id;

    private String name;

    private String email;

    private String username;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    private Boolean available = true;


}

