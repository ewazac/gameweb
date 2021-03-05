package com.example.demo.controllers;

import com.example.demo.mapper.ReviewMapper;
import com.example.demo.model.dao.Review;
import com.example.demo.repository.ReviewRepository;
import com.example.demo.model.dto.ReviewDto;
import com.example.demo.services.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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

    private final ReviewService reviewService;
    private final ReviewMapper reviewMapper;


    @PostMapping()
    public ReviewDto save(ReviewDto reviewDto) {
        return reviewMapper.toDto(reviewService.save(reviewMapper.toDao(reviewDto)));
    }

    @GetMapping("{id}")
    public ReviewDto getReviewById(@PathVariable String id) {
        return reviewMapper.toDto(reviewService.findReviewById(id));
    }

    @GetMapping()
    public List<ReviewDto> getReviews() {
        return reviewMapper.toListDto(reviewService.findAll());
    }

    @PutMapping("{id}")
    public ReviewDto updateReview(@RequestBody ReviewDto reviewDto, @PathVariable String id) {
        return reviewMapper.toDto(reviewService.update(reviewMapper.toDao(reviewDto), id));
    }

    @DeleteMapping("{id}")
    public void deleteReviewById(@PathVariable String id) {
        reviewService.deleteById(id);
    }

    @GetMapping("/game/{id}")
    public List<Review> getReviewsByGame(@PathVariable String id) {
        return reviewService.findReviewsByGameId(id);
    }

    @GetMapping("/ranking")
    public List<Review> getRanking() {
        return reviewService.findReviewsByStars();
    }

}
