package com.example.demo.mapper;

import com.example.demo.model.dao.Review;
import com.example.demo.model.dto.ReviewDto;
import com.example.demo.services.UserService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReviewMapper {


    public ReviewDto toDto(Review review) {
        return ReviewDto.builder()
                .description(review.getDescription())
                .stars(review.getStars())
                .title(review.getTitle())
                .gameId(review.getGameId())
                .id(review.getId())
                .nick(review.getNick())
                .build();
    }

    public Review toDao(ReviewDto reviewDto) {
        return Review.builder()
                .id(reviewDto.getId())
                .gameId(reviewDto.getGameId())
                .description(reviewDto.getDescription())
                .stars(reviewDto.getStars())
                .title(reviewDto.getTitle())
                .nick(reviewDto.getNick())
                .build();
    }

    public List<ReviewDto> toListDto(List<Review> reviewList) {
        return reviewList.stream().map(this::toDto).collect(Collectors.toList());
    }



}
