package com.example.demo.controllers;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Builder
@Getter
public class UserDTO {  //data transport object

    private String firstName;
    private String lastName;
    @Email
    @NotBlank
    private String email;

}
