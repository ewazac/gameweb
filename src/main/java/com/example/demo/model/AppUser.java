package com.example.demo.model;

import lombok.Builder;
import lombok.Getter;
import org.bson.types.Binary;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Document(collection = "user")
@Builder
public class AppUser {

    @Id
    public String id;

    public String password;
    private String firstName;
    private String lastName;
    private String email;
    private Binary avatar;

    private List<String> roles;

    public void setAvatar(Binary avatar) {
        this.avatar = avatar;
    }

    public Binary getAvatar() {
        return avatar;
    }
}





