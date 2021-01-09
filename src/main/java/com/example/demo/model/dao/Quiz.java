package com.example.demo.model.dao;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("quiz")
public class Quiz {


    @Id
    public String id;
    public String name;
    public String description;
    public List<Question> answers;

}
