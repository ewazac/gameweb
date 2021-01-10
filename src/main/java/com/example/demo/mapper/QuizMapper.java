package com.example.demo.mapper;


import com.example.demo.model.dao.Quiz;
import com.example.demo.model.dto.QuizDto;
import org.springframework.stereotype.Component;

@Component
public class QuizMapper {

    public Quiz toDao(QuizDto quizDto) {
        return Quiz.builder()
                .answers(quizDto.getAnswers())
                .description(quizDto.getDescription())
                .id(quizDto.getId())
                .name(quizDto.getName())
                .build();
    }

    public Quiz toDto(Quiz quiz) {
        return Quiz.builder()
                .name(quiz.getName())
                .id(quiz.getId())
                .description(quiz.getDescription())
                .answers(quiz.getAnswers())
                .build();
    }


}
