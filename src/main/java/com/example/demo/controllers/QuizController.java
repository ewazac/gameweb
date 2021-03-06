package com.example.demo.controllers;

import com.example.demo.mapper.QuizMapper;
import com.example.demo.model.dao.News;
import com.example.demo.model.dao.Quiz;
import com.example.demo.model.dto.QuizDto;
import com.example.demo.repository.QuizRepository;
import com.example.demo.services.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quizy")
@CrossOrigin(origins = {"https://gameweb2.herokuapp.com", "http://localhost:4200"}, allowCredentials = "true")
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;
    private final QuizMapper quizMapper;

    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public QuizDto saveQuiz(@RequestBody QuizDto quizDto) {
        return quizMapper.toDto(quizService.save(quizMapper.toDao(quizDto)));
    }

    @GetMapping()
    public List<QuizDto> getQuizList() {
        return quizMapper.toListDto(quizService.getAll());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public QuizDto updateQuiz(@RequestBody QuizDto quizDto, @PathVariable String id) {
        return quizMapper.toDto(quizService.update(quizMapper.toDao(quizDto), id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteQuizById(@PathVariable String id) {
        quizService.deleteById(id);
    }

    @GetMapping(value = "/getQuizBody/{quizId}", produces = MediaType.TEXT_HTML_VALUE)
    public String bodyAsHtml(@PathVariable String quizId) {
        return quizService.bodyAsHtml(quizId);
    }



}
