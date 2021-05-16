package com.example.demo.model.dao;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.bson.types.Binary;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;


@Document(collection = "games")
@Builder
@Data
public class Game {

    @Id
    public String id;
    private String name;
    private String appId;
    private final String description;
    private Binary gameImage;
    @DBRef
    private Set<User> users;
    private String groupId;
    private Integer counter;
    @CreatedDate
    private LocalDate createdDate;
    private boolean recommended;
    private LocalDate recommendedDate;
    private boolean sendMail;



}
