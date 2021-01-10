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
    private Boolean newsletter;
    private List<String> categories;

    public void setAvatar(Binary avatar) {
        this.avatar = avatar;
    }

    public Binary getAvatar() {
        return avatar;
    }

    public String getId() {
        return id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCategories(List<String> categories) {this.categories = categories;}

    public List<String> getRole() { return roles;}

    public String getEmail() { return email;}

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNewsletter(Boolean newsletter) { this.newsletter = newsletter;}

    public void setId(String id) {
        this.id = id;
    }



}





