package com.example.demo.controllers;


import com.example.demo.mapper.UserMapper;
import com.example.demo.model.dao.User;
import com.example.demo.model.dto.RestartDto;
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
@CrossOrigin(origins = {"https://gameweb2.herokuapp.com", "http://localhost:4200"}, allowCredentials = "true")
public class UserController {




    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping()
    public User getUser() {
        return userService.getCurrentUser();
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public UserDto createUser(@Valid @RequestBody UserDto userDto) throws UserFoundException {
        return userMapper.toDto(userService.save(userMapper.toDao(userDto)));
    }

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

    @PatchMapping()
    public void changeRestartPassword(@RequestBody RestartDto restartDto) throws Throwable {
        userService.changeRestartPassword(restartDto.getActivatedCode(), restartDto.getPassword());
    }

    @PutMapping(value = "/changePassword")
    public void changePassword(@RequestParam("newpassword") String newPassword, @RequestParam("oldpassword") String oldPassword) throws InvalidOldPasswordException {
        userService.changePassword(newPassword, oldPassword);
    }

    @PostMapping("/addNick")
    public void addNick(String nick) {
        userService.addNick(nick);
    }

    @PutMapping("/changeNick")
    public void changeNick(String newNick, String oldNick) {
        userService.changeNick(newNick, oldNick);
    }

    @PutMapping("/changeFirstName")
    public void changeFirstName(String newFirstName, String oldFirstName) {
        userService.changeFirstName(newFirstName, oldFirstName);
    }

    @PutMapping("/changeLastName")
    public void changeLastName(String newLastName, String oldLastName) {
        userService.changeLastName(newLastName, oldLastName);
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
