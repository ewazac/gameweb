package com.example.demo.mapper;


import com.example.demo.model.dao.Forum;
import com.example.demo.model.dao.Quiz;
import com.example.demo.model.dto.ForumDto;
import com.example.demo.model.dto.QuizDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

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

    public QuizDto toDto(Quiz quiz) {
        return QuizDto.builder()
                .name(quiz.getName())
                .id(quiz.getId())
                .description(quiz.getDescription())
                .answers(quiz.getAnswers())
                .build();
    }

    public List<QuizDto> toListDto(List<Quiz> quizList) {
        return quizList.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }


}
