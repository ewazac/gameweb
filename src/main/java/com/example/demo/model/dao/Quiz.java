package com.example.demo.model.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document("quiz")
public class Quiz {

    @Id
    public String id;
    public String name;
    public String description;
    public List<Option> answers;

}
