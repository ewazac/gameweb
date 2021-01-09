package com.example.demo.controllers;


import com.example.demo.exeption.EntityNotFoundException;
import com.example.demo.model.dao.AppUser;
import com.example.demo.repository.UserRepository;
import com.example.demo.model.dto.UserDto;
import com.example.demo.services.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
    private final UserService userService;



    @PostMapping()
    @ResponseStatus(value = HttpStatus.CREATED)
    public void createUser(@Valid @RequestBody UserDto userDTO) throws UserFoundException {
        AppUser appUser = userRepository.findByEmail(userDTO.getEmail()).orElseThrow(()-> new EntityNotFoundException(userDTO.email));
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

    @PutMapping ("/uploadAvatar")
    public void addAvatar(@RequestParam("avatar") MultipartFile multipartFile) throws IOException {
        AppUser user = userService.getCurrentUser();
        if (user != null) {
            user.setAvatar(new Binary(BsonBinarySubType.BINARY, multipartFile.getBytes()));
            userRepository.save(user);
        }
    }

    @GetMapping(value = "/getAvatar")
    public Binary getAvatar() {
        AppUser user = userService.getCurrentUser();
        return user.getAvatar();
    }

    @PutMapping(value = "/changePassword")
    public void changePassword(@RequestParam("newpassword") String newPassword, @RequestParam("oldpassword") String oldPassword) throws InvalidOldPasswordException {
        AppUser user = userService.getCurrentUser();
        if(!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new InvalidOldPasswordException();
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    @PutMapping(value = "/changeLogin")
    @ResponseStatus(value = HttpStatus.OK)
    public void changeLogin(@RequestParam("newlogin") String newLogin, @RequestParam("oldlogin") String oldLogin) {
        AppUser user = userService.getCurrentUser();
        if(newLogin.equals(oldLogin)) {
            System.out.println("Same login!");
        }
        user.setEmail(newLogin);
        userRepository.save(user);
    }


    @GetMapping()
    public AppUser getUser() {
        return userService.getCurrentUser();
    }


    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void exceptionHandler(UserFoundException userFoundException) {
        log.info("brak uzytkownika");
    }


}
