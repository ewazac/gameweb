package com.example.demo.model.dao;

import lombok.*;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "user")
@Builder

public class User {

    @Id
    public String id;
    public String password;
    private String firstName;
    private String lastName;
    private String email;
    private Binary avatar;
    private List<String> roles;
    private List<String> categories;
    private String activateCode;
    private String nick;

    private boolean newsletter;




}





