package com.example.demo.model.dto;

import com.example.demo.model.dao.Answer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.Binary;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ForumDto {

    private String id;
    private String name;
    private List<Answer> answers;
    private String category;

}
