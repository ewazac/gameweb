package com.example.demo.repository;

import com.example.demo.model.dao.Quiz;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface QuizRepository extends MongoRepository<Quiz, String> {

    Quiz findByName(String name);
}
