package com.example.todoapp.controllers;

import com.example.todoapp.dtos.UserDTO;
import com.example.todoapp.models.UserModel;
import com.example.todoapp.services.UserService;
import jakarta.validation.Valid;
import org.apache.catalina.User;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/user")
public class UserController {

    final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Object> createUser(@RequestBody @Valid UserDTO userDTO) {

        var userModel = new UserModel();

        BeanUtils.copyProperties(userDTO, userModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(userModel));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable(value = "id") Long id) {

        Optional<UserModel> userModelOptional = userService.findById(id);

        if(userModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não foi possível localizar esse usuário.");
        }

        userService.delete(userModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("O usuário foi deletado com sucesso.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable(value = "id") Long id, @RequestBody @Valid UserDTO userDTO) {

        Optional<UserModel> userModelOptional = userService.findById(id);

        if(userModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não foi possível localizar esse usuário.");
        }

        var userModel = new UserModel();

        BeanUtils.copyProperties(userDTO, userModel);
        userModel.setUser_id(userModelOptional.get().getUser_id());

        return ResponseEntity.status(HttpStatus.OK).body(userService.create(userModel));
    }
}
