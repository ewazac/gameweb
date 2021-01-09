package com.example.demo.controllers;

import com.example.demo.model.dao.Quiz;
import com.example.demo.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/quizy")
@RequiredArgsConstructor
public class QuizController {

    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public Quiz saveQuiz(@RequestBody Quiz quiz) {
        return null;
    }
}
