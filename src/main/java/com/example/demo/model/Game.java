package com.example.demo.model;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.StringUtils;

@Getter
@Document(collection = "games")
@Builder
public class Game {

    @Id
    public String id;
    private String name;
    private String category;
    private String description;
    private String platform;



//    public Game(String id, String name, String category) {
//        this.id = id;
//        this.name = name;
//        this.category = category;
//    }


//    public void setId(String id) {
//        if (StringUtils.isEmpty(this.id)) {
//            this.id = id;
//        }
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getCategory() {
//        return category;
//    }
//
//    public void setCategory(String category) {
//        this.category = category;

}
