package com.example.todoapp.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserDTO {

    @NotNull
    private String username;

    @NotNull
    private String email;

    @NotNull
    private String password;
}
