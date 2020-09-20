package com.example.demo.controllers;


import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Builder
@Getter
public class ReviewDTO {

    @NotBlank
    public String title;
    @NotBlank
    public String description;
    public Float stars;


}
