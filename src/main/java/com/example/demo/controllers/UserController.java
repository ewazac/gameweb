package com.example.demo.controllers;


import com.example.demo.mapper.UserMapper;
import com.example.demo.model.dao.Review;
import com.example.demo.model.dao.User;
import com.example.demo.model.dto.RestartDto;
import com.example.demo.model.dto.UserDto;
import com.example.demo.services.ReviewService;
import com.example.demo.services.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.Binary;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;


@AllArgsConstructor
@CorsRestController
@RequestMapping("/users")
@Slf4j
@CrossOrigin(origins = {"https://gameweb2.herokuapp.com", "http://localhost:4200"}, allowCredentials = "true")
public class UserController {


    private final UserService userService;
    private final UserMapper userMapper;
    private final ReviewService reviewService;

    @GetMapping()
    public User getUser() {
        return userService.getCurrentUser();
    }

    @GetMapping("/{id}")
    public Optional<User> getUserById(@PathVariable String id) {
        return userService.getUserById(id);
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

    @GetMapping("reviews")
    public List<Review> getAllReviewsForUser() {
        return reviewService.getAllReviewsForUser();
    }

    @PutMapping(value = "/changePassword")
    public void changePassword(@RequestParam("newpassword") String newPassword, @RequestParam("oldpassword") String oldPassword) throws InvalidOldPasswordException {
        userService.changePassword(newPassword, oldPassword);
    }

    @PatchMapping("/updateUser")
    @PreAuthorize("isAuthenticated()")
    public UserDto updateUser(@RequestBody UserDto userDto) {
        return userMapper.toDto(userService.updateUser(userMapper.toDaoWithoutPassword(userDto)));
    }

    @PostMapping("/restart")
    public void restartPassword(@RequestParam String email) {
        userService.restartPassword(email);
    }

    @PatchMapping("/newsletter")
    public void changeNewsletter() {
        userService.changeNewsletter();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void exceptionHandler(UserFoundException userFoundException) {
        log.info("No user found");
    }
}
