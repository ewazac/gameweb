package com.example.demo.model;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ReviewRepository extends MongoRepository<Review, String> {

    Review findReviewByTitle(String title);

    List<Review> findReviewByGame(String game);

    List<Review> findReviewByStars(Float stars);

    Review findReviewByDescription(String description);
}
