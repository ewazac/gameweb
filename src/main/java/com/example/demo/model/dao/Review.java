package com.example.demo.model.dao;


import lombok.*;

import org.bson.types.Binary;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "reviews")
public class Review {

    @Id
    private String id;
    private String title;
    private String description;
    private Float stars;
    private String gameId;
    private Binary image;
    @CreatedDate
    private LocalDate createdDate;
    @CreatedBy
    private String createdBy;
    private String nick;
    private String userId;

}
