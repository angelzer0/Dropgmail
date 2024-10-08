package com.dropgmail.model.dto;

import com.dropgmail.model.entities.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;


@Builder
@Data
public class UsersUpdateDto {

    private Integer id;

    @NotBlank(message = "name is required")
    @Min(value = 3, message = "Name cannot be less than 3 characters")
    private String name;

    @NotBlank(message = "Username is required")
    @Min(value = 5, message = "username cannot be less than 5 characters")
    private String username;

    @NotBlank(message = "Password is required")
    @Min(value = 6, message = "The password cannot be less than 6 characters")
    private String password;

    @Email(message = "Email should be valid")
    private String email;

    private Role role;
    private Boolean available = true;

}
