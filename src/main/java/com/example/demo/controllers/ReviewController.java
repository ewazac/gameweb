package com.example.demo.controllers;

import com.example.demo.model.Review;
import com.example.demo.model.ReviewRepository;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import lombok.AllArgsConstructor;
import org.apache.catalina.valves.rewrite.RewriteCond;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.management.Query;
import javax.swing.text.Document;
import javax.validation.Valid;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static com.mongodb.client.model.Accumulators.avg;
import static java.util.Collections.sort;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import static org.springframework.data.mongodb.core.query.Criteria.where;

@RestController
@AllArgsConstructor
@RequestMapping("/reviews")
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


        Aggregation aggregation = newAggregation(group("game").avg("stars").as("average"), project("average").and("game").previousOperation());
        AggregationResults<Review> results = mongoTemplate.aggregate(aggregation, "reviews", Review.class);
        List<Review> finalResult = results.getMappedResults();
        return finalResult;

    }
      /*

Arrays.asList(group("$game", avg("avg", "$stars")))
 */
























    @GetMapping
    public List<Review> getReviews() {return reviewRepository.findAll();}






}
