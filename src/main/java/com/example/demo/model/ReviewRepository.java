package com.example.demo.model;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

import static com.mongodb.client.model.Filters.where;

public interface ReviewRepository extends MongoRepository<Review, String> {

    Review findReviewByTitle(String title);

    List<Review> findReviewByGame(String game);

    List<Review> findReviewByStars(Float stars);

    Review findReviewByDescription(String description);

    List findByGameOrderByStarsDesc(String game);





}
