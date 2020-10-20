package com.example.demo.controllers;

import com.example.demo.model.AppUser;
import com.example.demo.model.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.*;



@AllArgsConstructor
@CorsRestController
@RequestMapping("/users")
@Slf4j
@CrossOrigin(origins = "https://gameweb2.herokuapp.com", allowCredentials = "true")
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @PostMapping()
    @ResponseStatus(value = HttpStatus.CREATED)
    public void createUser(@Valid @RequestBody UserDTO userDTO) throws UserFoundException {
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

    @RequestMapping(value ="/uploadAvatar", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public void addAvatar(@RequestParam("avatar") MultipartFile multipartFile) throws IOException {
        UserDetails userDetails =
                (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();  //getting user from session
        userDetails.getUsername();
        AppUser user = userRepository.findUserByEmail(userDetails.getUsername());
        if (user != null) {
            user.setAvatar(new Binary(BsonBinarySubType.BINARY, multipartFile.getBytes()));
            userRepository.save(user);
        }
    }

    @GetMapping(value = "/getAvatar")
    public Binary getAvatar() {
        UserDetails userDetails =
                (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();  //getting user from session
        userDetails.getUsername();
        AppUser appUser = userRepository.findUserByEmail(userDetails.getUsername());
        return appUser.getAvatar();

    }

    @PutMapping(value = "/changePassword")
    @ResponseStatus(value = HttpStatus.OK)
    public void changePassword(@RequestParam("newpassword") String newPassword, @RequestParam("oldpassword") String oldPassword) throws InvalidOldPasswordException {
        UserDetails userDetails =
                (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();  //getting user from session
        userDetails.getUsername();
        AppUser appUser = userRepository.findUserByEmail(userDetails.getUsername());
        if(!passwordEncoder.matches(oldPassword, appUser.getPassword())) {
            throw new InvalidOldPasswordException();
        }
        appUser.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(appUser);
    }


    @GetMapping()
    public AppUser getUser() {
        UserDetails userDetails =
                (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();  //getting user from session
        return userRepository.findUserByEmail(userDetails.getUsername());
    }


    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void exceptionHandler(UserFoundException userFoundException) {
        log.info("brak uzytkownika");
    }


}
