package com.example.demo.model;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface QuizRepository extends MongoRepository<Quiz, String> {
}
