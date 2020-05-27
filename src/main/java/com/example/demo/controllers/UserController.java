package com.example.demo.controllers;

import com.example.demo.model.AppUser;
import com.example.demo.model.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@RequestMapping("/users")
@AllArgsConstructor
@CorsRestController
@Slf4j
public class UserController {

    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public void createUser(@Valid @RequestBody UserDTO userDTO) throws UserFoundException{
        AppUser appUser = userRepository.findUserByEmail(userDTO.getEmail());
        if(appUser != null) {
            throw new UserFoundException();
        }
        AppUser user = AppUser.builder()
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .email(userDTO.getEmail())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .roles(Arrays.asList("USER"))
                .id(UUID.randomUUID().toString()).build();
        userRepository.save(user);
    }
    
    @GetMapping
    public List<AppUser> getUsers() {
        return userRepository.findAll();
    }

    @GetMapping(value = "/{id}")
    public Optional<AppUser> getUser(@PathVariable String id) {
        return userRepository.findById(id);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void exceptionHandler(UserFoundException userFoundException) {
        log.info("brak uzytkownika");
    }


}
