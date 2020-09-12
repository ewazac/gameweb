package com.example.demo.model;

import lombok.Builder;
import lombok.Getter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Document(collection = "user" )
@Builder
public class AppUser {

    @Id
    public String id;

    public String password;
    private String firstName;
    private String lastName;
    private String email;

    private List<String> roles;
}





