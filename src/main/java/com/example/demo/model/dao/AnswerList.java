package com.example.demo.model.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** AnswerList class is used for quizzes. It contains list of questions and answers*/
public class AnswerList {

    private String question;
    private List<Option> option;
}
