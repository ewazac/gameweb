package com.example.demo.model.dto;


import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Builder
@Getter
public class ReviewDto {

    @NotBlank
    public String title;
    @NotBlank
    public String description;
    public Float stars;
    public String game;


}
