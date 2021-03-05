package com.example.demo.repository;

import com.example.demo.model.dao.Review;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ReviewRepository extends MongoRepository<Review, String> {

    Review findReviewByTitle(String title);

    List<Review> findReviewByStars(Float stars);

    Review findReviewByDescription(String description);

    List<Review> findByGameIdOrderByStarsDesc(String gameId);

    List<Review> findByGameId(String gameId);

    List<Review> findReviewByGameIdOrderByStarsDesc();

    List<Review> findFirst20OrderByStars();


}
