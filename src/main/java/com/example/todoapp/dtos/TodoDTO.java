package com.example.todoapp.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TodoDTO {

    @NotNull
    private String title;

    @NotNull
    private String description;

}
