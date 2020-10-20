package com.example.demo.controllers;

import com.example.demo.model.Review;
import com.example.demo.model.ReviewRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

import java.util.List;
import java.util.UUID;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;


@RestController
@AllArgsConstructor
@RequestMapping("/reviews")
@CrossOrigin(origins = "https://gameweb2.herokuapp.com", allowCredentials = "true")
public class ReviewController {

    ReviewRepository reviewRepository;
    @Autowired
    MongoTemplate mongoTemplate;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public void createReview(@Valid @RequestBody ReviewDTO reviewDTO) {
        Review review = Review.builder()
                .title(reviewDTO.getTitle())
                .description(reviewDTO.getDescription())
                .stars(reviewDTO.getStars())
                .game(reviewDTO.getGame())
                .id(UUID.randomUUID().toString()).build();
        reviewRepository.save(review);
    }

    @GetMapping(value = "/game/{game}")
    public List<Review> getReviewsByGame(@PathVariable String game)
    {
        return reviewRepository.findReviewByGame(game);
    }


    @GetMapping(value = "/stars/{stars}")
    public List<Review> getReviewsByStars(@PathVariable Float stars) {
        return reviewRepository.findReviewByStars(stars);
    }

    @GetMapping(value = "/ranking")
    public List<Review> getRanking() {


        Aggregation aggregation = newAggregation(group("game").avg("stars").as("stars"), project("stars").and("game").previousOperation());
        AggregationResults<Review> results = mongoTemplate.aggregate(aggregation, "reviews", Review.class);
        List<Review> finalResult = results.getMappedResults();
        return finalResult;

    }


//Arrays.asList(group("$game", avg("avg", "$stars")))
























    @GetMapping
    public List<Review> getReviews() {return reviewRepository.findAll();}






}
