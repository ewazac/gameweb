package com.example.demo.model;

import lombok.Builder;
import lombok.Getter;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.StringUtils;

import java.util.List;

@Getter
@Document(collection = "games")
@Builder
public class Game {

    @Id
    public String id;
    private String name;
    private String category;
    private final String description;
    private final String platform;
    private Binary gameImage;
    @DBRef
    private final Review reviews;




    public void setId(String id) {
        if (StringUtils.isEmpty(this.id)) {
            this.id = id;
        }
    }

    public void setImage(Binary gameImage) {
        this.gameImage = gameImage;
    }

    public Binary getImage(Binary gameImage) {
        return gameImage;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;

}

}
