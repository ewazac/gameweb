package com.example.demo.mapper;

import com.example.demo.model.dao.Quiz;
import com.example.demo.model.dto.QuizDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class QuizMapper {

    public Quiz toDao(QuizDto quizDto) {
        return Quiz.builder()
                .quizList((quizDto.getAnswerList()))
                .sendMail(quizDto.isSendMail())
                //.description(quizDto.getDescription())
                .id(quizDto.getId())
                .name(quizDto.getName())
                .body(quizDto.getBody())

                .build();
    }

    public QuizDto toDto(Quiz quiz) {
        return QuizDto.builder()
                .name(quiz.getName())
                .id(quiz.getId())
                //.description(quiz.getDescription())
                .answerList(quiz.getQuizList())
                .body(quiz.getBody())
                .build();
    }

    public List<QuizDto> toListDto(List<Quiz> quizList) {
        return quizList.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }


}
