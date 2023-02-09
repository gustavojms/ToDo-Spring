package com.example.todoapp.controllers;

import com.example.todoapp.dtos.TodoDTO;
import com.example.todoapp.models.TodoModel;
import com.example.todoapp.services.TodoService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/todo")
public class TodoController {

    final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @PostMapping
    public ResponseEntity<Object> createTodo(@RequestBody @Valid TodoDTO todoDTO) {

        var todoModel = new TodoModel();

        BeanUtils.copyProperties(todoDTO, todoModel);
        todoModel.setCreatedAt(LocalDateTime.now(ZoneId.of("UTC-3")));
        return ResponseEntity.status(HttpStatus.CREATED).body(todoService.create(todoModel));
    }

    @GetMapping
    public ResponseEntity<List<TodoModel>> getAllTodos() {

        return ResponseEntity.status(HttpStatus.OK).body(todoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneTodos(@PathVariable(value = "id") Long id) {

        Optional<TodoModel> todoModelOptional = todoService.findById(id);

        if(todoModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não foi possível localizar essa tarefa.");
        }

        return ResponseEntity.status(HttpStatus.OK).body(todoModelOptional.get());

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTodos(@PathVariable(value = "id") Long id) {

        Optional<TodoModel> todoModelOptional = todoService.findById(id);

        if(todoModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Essa tarefa é inexistente.");
        }

        todoService.delete(todoModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("A tarefa foi deletada com sucesso!");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateTodos(@PathVariable(value = "id") Long id, @RequestBody @Valid TodoDTO todoDTO) {

        Optional<TodoModel> todoModelOptional = todoService.findById(id);

        if(todoModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não foi possível localizar essa tarefa.");
        }

        var todoModel = new TodoModel();

        BeanUtils.copyProperties(todoDTO, todoModel);
        todoModel.setId(todoModelOptional.get().getId());
        todoModel.setCreatedAt(todoModelOptional.get().getCreatedAt());

        return ResponseEntity.status(HttpStatus.OK).body(todoService.create(todoModel));
    }
}
