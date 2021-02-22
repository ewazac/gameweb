package com.example.demo.mapper;

import com.example.demo.model.dao.User;
import com.example.demo.model.dto.UserDto;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.Arrays;
import java.util.UUID;


@Component
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    public UserMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    public User toDao(UserDto userDto) {

        return User.builder()
                .categories(userDto.getCategories())
                .id(UUID.randomUUID().toString())
                .avatar(userDto.getAvatar())
                .email(userDto.getEmail())
                .newsletter(userDto.isNewsletter())
                .firstName(userDto.getFirstName())
                .nick(userDto.getNick())
                .lastName(userDto.getLastName())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .roles(Arrays.asList("USER"))
                .build();
    }

    public UserDto toDto(User user) {
        return UserDto.builder()
                .categories(user.getCategories())
                .id(user.getId())
                .avatar(user.getAvatar())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .newsletter(user.isNewsletter())
                .password(user.getPassword())
                .nick(user.getNick())
                .roles(user.getRoles())
                .build();
    }

    public User toDaoWithoutPassword(UserDto userDto) {

        return User.builder()
                .categories(userDto.getCategories())
                .id(UUID.randomUUID().toString())
                .avatar(userDto.getAvatar())
                .email(userDto.getEmail())
                .newsletter(userDto.isNewsletter())
                .firstName(userDto.getFirstName())
                .nick(userDto.getNick())
                .lastName(userDto.getLastName())
                .password(userDto.getPassword())
                .roles(Arrays.asList("USER"))
                .build();
    }

//    public List<UserDto> toListDto(List<User> userList) {
//        return userList.stream()
//                .map(this::toDto)
//                .collect(Collectors.toList());
//    }



}
