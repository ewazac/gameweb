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
    private final String id;
    private final String title;
    private final String description;
    private final Float stars;
    private final String game;


}
