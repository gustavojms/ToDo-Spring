package com.example.todoapp.services;

import com.example.todoapp.models.UserModel;
import com.example.todoapp.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public UserModel create(UserModel userModel) {
        return userRepository.save(userModel);
    }
}
