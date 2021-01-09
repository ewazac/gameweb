package com.example.demo.mapper;

import com.example.demo.model.dao.AppUser;
import com.example.demo.model.dao.Forum;
import com.example.demo.model.dto.ForumDto;
import com.example.demo.model.dto.UserDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public AppUser toDao(UserDto userDto) {
        return AppUser.builder()
                .categories(userDto.getCategories())
                .id(userDto.getId())
                .avatar(userDto.getAvatar())
                .email(userDto.getEmail())
                .newsletter(userDto.getNewsletter())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .password(userDto.getPassword())
                .roles(userDto.getRoles())
                .build();
    }

    public UserDto toDto(AppUser appUser) {
        return UserDto.builder()
                .categories(appUser.getCategories())
                .id(appUser.getId())
                .avatar(appUser.getAvatar())
                .email(appUser.getEmail())
                .firstName(appUser.getFirstName())
                .lastName(appUser.getLastName())
                .newsletter(appUser.getNewsletter())
                .password(appUser.getPassword())
                .roles(appUser.getRoles())
                .build();
    }

    public List<UserDto> toListDto(List<AppUser> userList) {
        return userList.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }



}
