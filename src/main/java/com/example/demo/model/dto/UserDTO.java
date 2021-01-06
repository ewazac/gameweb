package com.example.demo.controllers;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Builder
@Getter
public class UserDTO {  //data transport object

    public String firstName;
    public String lastName;
    @Email
    @NotBlank
    public String email;
    public String password;

}
