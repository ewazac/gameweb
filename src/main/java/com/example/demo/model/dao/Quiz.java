package com.example.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("quiz")
public class Quiz {


    @Id
    public String id;
    public String name;
    public String description;
    public String correctAnswer;

}
