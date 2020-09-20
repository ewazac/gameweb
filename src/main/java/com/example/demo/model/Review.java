package com.example.demo.model;


import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Document(collection = "reviews")
@Builder
public class Review {

    @Id
    private String id;
    private String title;
    private String description;
    private Float stars;
    private String game;


}
