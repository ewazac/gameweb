package com.example.demo.controllers;

import com.example.demo.mapper.QuizMapper;
import com.example.demo.model.dto.QuizDto;
import com.example.demo.services.QuizService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quizy")
@CrossOrigin(origins = {"https://gameweb.projektstudencki.pl", "http://localhost:4200"}, allowCredentials = "true")
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;
    private final QuizMapper quizMapper;

    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Add quiz by administrator")
    public QuizDto saveQuiz(@RequestBody QuizDto quizDto) {
        return quizMapper.toDto(quizService.save(quizMapper.toDao(quizDto)));
    }

    @GetMapping()
    @ApiOperation(value = "Get list of all quizzes")
    public List<QuizDto> getQuizList() {
        return quizMapper.toListDto(quizService.getAll());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Update quiz by administrator")
    public QuizDto updateQuiz(@RequestBody QuizDto quizDto, @PathVariable String id) {
        return quizMapper.toDto(quizService.update(quizMapper.toDao(quizDto), id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Delete quiz by administrator")
    public void deleteQuizById(@PathVariable String id) {
        quizService.deleteById(id);
    }

//    @GetMapping(value = "/getQuizBody/{quizId}", produces = MediaType.TEXT_HTML_VALUE)
//    public String bodyAsHtml(@PathVariable String quizId) {
//        return quizService.bodyAsHtml(quizId);
//    }



}
