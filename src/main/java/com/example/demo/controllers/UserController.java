package com.example.demo.controllers;

import com.example.demo.model.AppUser;
import com.example.demo.model.UserRepository;
import com.example.demo.services.MongoUserDetailsService;
import com.fasterxml.jackson.databind.ser.Serializers;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.*;


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

    @RequestMapping(value ="/{id}/uploadAvatar", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public void addAvatar(@RequestParam("avatar") MultipartFile multipartFile, @PathVariable String id) throws IOException {
        AppUser user = userRepository.findUserById(id);
        if (user != null) {
            user.setAvatar(new Binary(BsonBinarySubType.BINARY, multipartFile.getBytes()));
            userRepository.save(user);
        }
    }

    @GetMapping(value = "/{id}/getAvatar")
    public Binary getAvatar(@PathVariable String id) {
        AppUser appUser = userRepository.findUserById(id);
        return appUser.getAvatar();

    }

    @PostMapping(value = "/changePassword")
    @ResponseStatus(value = HttpStatus.OK)
    public void changePassword(@RequestParam("newpassword") String newPassword, @RequestParam("oldpassword") String oldPassword) throws InvalidOldPasswordException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUser = authentication.getName();
        AppUser appUser = userRepository.findUserByEmail(currentUser);
        if(!passwordEncoder.matches(oldPassword, appUser.getPassword())) {
            throw new InvalidOldPasswordException();
        }
        appUser.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(appUser);

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
