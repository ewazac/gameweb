package com.example.demo.model;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReviewRepository extends MongoRepository<Review, String> {

    Review findReviewByTitle(String title);
}
