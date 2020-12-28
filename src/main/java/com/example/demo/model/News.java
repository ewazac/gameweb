package com.example.demo.model;

import lombok.Builder;
import lombok.Getter;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Getter
@Document("news")
@Builder
public class News {

    @Id
    private String id;
    private String title;
    private String description;
    private Binary image;


    public void setImage(Binary image) {
        this.image = image;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
