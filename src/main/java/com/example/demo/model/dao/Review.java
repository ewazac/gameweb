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
/**Review class is used for creating reviews by users*/
public class Review {

    @Id
    private String id;
    private String title;
    private String description;
    private Float stars;
    private String gameId;
    private String gameName;
    private Binary image;
    @CreatedDate
    private LocalDate createdDate;
    @CreatedBy
    private String createdBy;
    private String nick;
    private String userId;
    private String imageUrl;
    private boolean accepted;


}
