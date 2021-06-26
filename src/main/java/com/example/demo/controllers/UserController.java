package com.example.demo.controllers;

import com.example.demo.mapper.UserMapper;
import com.example.demo.model.dao.Review;
import com.example.demo.model.dao.User;
import com.example.demo.model.dto.RestartDto;
import com.example.demo.model.dto.UserDto;
import com.example.demo.services.ReviewService;
import com.example.demo.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
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
@Api(value = "users", description = "User's profile operations")
@CrossOrigin(origins = {"https://gameweb.projektstudencki.pl", "http://localhost:4200"}, allowCredentials = "true")
public class UserController {


    private final UserService userService;
    private final UserMapper userMapper;
    private final ReviewService reviewService;

    @GetMapping()
    @ApiOperation(value = "Log in user", response = Iterable.class)
    public User getUser() {
        return userService.getCurrentUser();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Returns user's profile by id")
    public Optional<User> getUserById(@PathVariable String id) {
        return userService.getUserById(id);
    }

    @PostMapping
    @ApiOperation(value = "Sign up user")
    @ResponseStatus(value = HttpStatus.CREATED)
    public UserDto createUser(@Valid @RequestBody UserDto userDto) throws UserFoundException {
        return userMapper.toDto(userService.save(userMapper.toDao(userDto)));
    }

    @GetMapping("/details/{id}")
    @ApiOperation(value = "Returns user details by id that can be seen by other users")
    public UserDto getUserDetails(@PathVariable String id) {
        return userService.getUserDetails(id);
    }

    @PutMapping ("/uploadAvatar")
    @PreAuthorize("isAuthenticated()")
    @ApiOperation(value = "Upload avatar to user's profile")
    public void addAvatar(@RequestParam("avatar") MultipartFile multipartFile) throws IOException {
        userService.uploadAvatar(multipartFile);
    }


    @PatchMapping("/restart")
    @ApiOperation(value = "Change password from sent email")
    public void changeRestartPassword(@RequestBody RestartDto restartDto) throws Throwable {
        userService.changeRestartPassword(restartDto.getActivatedCode(), restartDto.getPassword());
    }

    @GetMapping("reviews")
    @ApiOperation(value = "Get list of all reviews wrote by user")
    public List<Review> getAllReviewsForUser() {
        return reviewService.getAllReviewsForUser();
    }

    @PutMapping(value = "/changePassword")
    @ApiOperation(value = "Change user's password in profile")
    public void changePassword(@RequestParam("newpassword") String newPassword, @RequestParam("oldpassword") String oldPassword) throws InvalidOldPasswordException {
        userService.changePassword(newPassword, oldPassword);
    }

    @PatchMapping("/updateUser")
    @PreAuthorize("isAuthenticated()")
    @ApiOperation(value = "Update data at user's profile")
    public UserDto updateUser(@RequestBody UserDto userDto) {
        return userMapper.toDto(userService.updateUser(userMapper.toDaoWithoutPassword(userDto)));
    }

    @PostMapping("/restart")
    @ApiOperation(value = "Method sends email to user with link to restart password")
    public void restartPassword(@RequestParam String email) {
        userService.restartPassword(email);
    }

    @PatchMapping("/newsletter")
    @ApiOperation(value = "Subscribe to the newsletter")
    public void changeNewsletter() {
        userService.changeNewsletter();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void exceptionHandler(UserFoundException userFoundException) {
        log.info("No user found");
    }
}
