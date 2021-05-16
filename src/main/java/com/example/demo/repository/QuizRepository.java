package com.example.demo.repository;

import com.example.demo.model.dao.Quiz;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.List;

public interface QuizRepository extends MongoRepository<Quiz, String> {

    Quiz findByName(String name);

    List<Quiz> findByCreatedDateIsAfter(LocalDate date);

    List<Quiz> findByCreatedDateIsAfterAndSendMailIsTrue(LocalDate minusDays);
}
