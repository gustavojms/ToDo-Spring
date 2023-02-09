package com.example.todoapp.services;

import com.example.todoapp.models.TodoModel;
import com.example.todoapp.repositories.TodoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoService {

    final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Transactional
    public TodoModel create(TodoModel todoModel) {
        return todoRepository.save(todoModel);
    }

    public List<TodoModel> findAll() {
        return todoRepository.findAll();
    }

    public Optional<TodoModel> findById(Long id) {
        return todoRepository.findById(id);
    }

    @Transactional
    public void delete(TodoModel todoModel) {
        todoRepository.delete(todoModel);
    }
}
