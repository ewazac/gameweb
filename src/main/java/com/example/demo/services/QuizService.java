package com.example.demo.services;


import com.example.demo.exeption.EntityNotFoundException;
import com.example.demo.model.dao.Quiz;
import com.example.demo.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final QuizRepository quizRepository;

    public List<Quiz> getAll() { return quizRepository.findAll();}

    public Quiz save(Quiz quiz) { return quizRepository.save(quiz);}

    public Quiz update(Quiz quiz, String id) {
        Quiz quizDb = getById(id);
        quizDb.setName(quiz.getName());
        quizDb.setAnswers(quiz.getAnswers());
        quizDb.setDescription(quiz.getDescription());
        return save(quizDb);
    }

    public Quiz getById(String id) {
        return quizRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id));
    }






}
