package com.example.demo.model.dao;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Set;


@Document(collection = "games")
@Builder
@Data
public class Game {

    @Id
    public String id;
    private String name;
    private String category;
    private final String description;
    private final String platform;
    private Binary gameImage;
//    @DBRef
//    private final Review reviews;
    @DBRef
    private Set<User> users;
    private String groupId;
    private long counter;





}
