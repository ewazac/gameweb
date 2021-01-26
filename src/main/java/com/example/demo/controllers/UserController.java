package com.example.demo.controllers;

import com.example.demo.exeption.EntityNotFoundException;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.dao.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.model.dto.UserDto;
import com.example.demo.services.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.Binary;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.Valid;
import java.io.IOException;


@AllArgsConstructor
@CorsRestController
@RequestMapping("/users")
@Slf4j
@CrossOrigin(origins = "https://gameweb2.herokuapp.com", allowCredentials = "true")
public class UserController {



    private final UserRepository userRepository;
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping()
    public User getUser() {
        return userService.getCurrentUser();
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public UserDto createUser(@Valid @RequestBody UserDto userDto) throws UserFoundException {
//        User user = userRepository.findByEmail(userDto.getEmail()).orElseThrow(()-> new EntityNotFoundException(userDto.email));
//        if(user != null) {
//            throw new UserFoundException();
//        }
        return userMapper.toDto(userService.save(userMapper.toDao(userDto)));
    }

//    @PostMapping()
//    @ResponseStatus(value = HttpStatus.CREATED)
//    public void createUser(@Valid @RequestBody UserDto userDTO) throws UserFoundException {
//        User appUser = userRepository.findByEmail(userDTO.getEmail()).orElseThrow(()-> new EntityNotFoundException(userDTO.email));
//        if(appUser != null) {
//            throw new UserFoundException();
//        }
//        User user = User.builder()
//                .firstName(userDTO.getFirstName())
//                .lastName(userDTO.getLastName())
//                .email(userDTO.getEmail())
//                .password(passwordEncoder.encode(userDTO.getPassword()))
//                .roles(Arrays.asList("USER"))
//                .id(UUID.randomUUID().toString()).build();
//        userRepository.save(user);
//    }

    @PutMapping ("/uploadAvatar")
    @PreAuthorize("isAuthenticated()")
    public void addAvatar(@RequestParam("avatar") MultipartFile multipartFile) throws IOException {
        userService.uploadAvatar(multipartFile);
    }

    @GetMapping(value = "/getAvatar")
    public Binary getAvatar() {
        User user = userService.getCurrentUser();
        return user.getAvatar();
    }

    @PutMapping(value = "/changePassword")
    public void changePassword(@RequestParam("newpassword") String newPassword, @RequestParam("oldpassword") String oldPassword) throws InvalidOldPasswordException {
        userService.changePassword(newPassword, oldPassword);
    }

    @PutMapping(value = "/changeLogin")
    @PreAuthorize("isAuthenticated()")
    public void changeLogin(@RequestParam("newlogin") String newLogin, @RequestParam("oldlogin") String oldLogin) {
        userService.changeLogin(newLogin, oldLogin);
    }

    @PostMapping("/restart")
    public void restartPassword(@RequestParam String email) {
        userService.restartPassword(email);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void exceptionHandler(UserFoundException userFoundException) {
        log.info("No user found");
    }


}
