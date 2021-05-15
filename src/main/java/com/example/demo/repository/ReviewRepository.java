package com.example.demo.repository;

import com.example.demo.model.dao.Review;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ReviewRepository extends MongoRepository<Review, String> {


    List<Review> findByGameId(String gameId);

    List<Review> findByUserId(String userId);


    List<Review> findByUserIdAndAcceptedIsTrue(String currentUser);

    List<Review> findByGameIdAndAcceptedIsTrue(String gameId);

    List<Review> findByAcceptedIsTrue();
}
