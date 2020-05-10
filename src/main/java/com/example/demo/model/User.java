package com.example.demo.model;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document
@Builder
public class User {

    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String email;


}
