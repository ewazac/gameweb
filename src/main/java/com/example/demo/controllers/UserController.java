package com.example.demo.controllers;

import com.example.demo.model.User;
import com.example.demo.model.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@RequestMapping("/users")
@AllArgsConstructor
@CorsRestController
public class UserController {

    UserRepository userRepository;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public void createUser(@Valid @RequestBody UserDTO userDTO) {
        User user = User.builder()
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .email(userDTO.getEmail())
                .id(UUID.randomUUID().toString()).build();
        userRepository.save(user);
    }

    @GetMapping
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @GetMapping(value = "/{id}")
    public Optional<User> getUser(@PathVariable String id) {
        return userRepository.findById(id);
    }



}
