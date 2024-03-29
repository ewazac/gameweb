package com.example.demo.repository;

import com.example.demo.model.dao.Review;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ReviewRepository extends MongoRepository<Review, String> {


    List<Review> findByUserIdAndAcceptedIsTrue(String currentUser);

    List<Review> findByGameIdAndAcceptedIsTrue(String gameId);

    List<Review> findByAcceptedIsTrue();

    List<Review> findByAcceptedIsFalse();
}
