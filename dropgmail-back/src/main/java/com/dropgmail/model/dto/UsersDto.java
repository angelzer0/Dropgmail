package com.dropgmail.model.dto;

import com.dropgmail.model.entities.Role;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UsersDto {
    private Integer id;
    private String name;
    private String username;
    private String password;
    private String email;
    private Role role;
    private Boolean available = true;
}
