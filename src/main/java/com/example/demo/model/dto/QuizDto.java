package com.example.demo.model.dto;

import com.example.demo.model.dao.AnswerList;
import com.example.demo.model.dao.Option;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuizDto {

    public String id;
    public String name;
    public String description;
    //public List<Option> answers;
    private String body;
    private List<AnswerList> answerList;



}
