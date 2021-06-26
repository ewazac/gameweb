package com.example.demo.services;


import com.example.demo.exeption.EntityNotFoundException;
import com.example.demo.model.dao.Quiz;
import com.example.demo.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final QuizRepository quizRepository;

    /**Returns all quizzes*/
    public List<Quiz> getAll() { return quizRepository.findAll();}

    /**Saves quiz*/
    public Quiz save(Quiz quiz) { return quizRepository.save(quiz);}


    /**Updates quiz*/
    public Quiz update(Quiz quiz, String id) {
        Quiz quizDb = getById(id);
        quizDb.setName(quiz.getName());
        quizDb.setQuizList(quiz.getQuizList());
        return save(quizDb);
    }

    /**Returns quiz by id*/
    public Quiz getById(String id) {
        return quizRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id));
    }

    /**Deletes quiz by id*/
    public void deleteById(String id) {
        quizRepository.deleteById(id);
    }


}
