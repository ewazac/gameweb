package com.example.demo.model.dao;

import lombok.*;
import org.bson.types.Binary;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document("news")
public class News {

    @Id
    private String id;
    private String title;
    private String description;
    private Binary image;
    @CreatedDate
    private LocalDate createdDate;
    private String body;


}
