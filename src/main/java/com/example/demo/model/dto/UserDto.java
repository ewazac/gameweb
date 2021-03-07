package com.example.demo.model.dto;

import lombok.*;
import org.bson.types.Binary;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {  //data transport object

    public String id;
    public String firstName;
    public String lastName;
    @Email
    @NotBlank
    public String email;
    public String password;
    private Binary avatar;
    private List<String> roles;
    private boolean newsletter;
    private List<String> categories;
    private String nick;
    private int point;

}
