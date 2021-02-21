package com.example.demo.model.dto;


import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Builder
@Getter
public class ReviewDto {

    @NotBlank
    private String title;
    @NotBlank
    private String description;
    private Float stars;
    private String gameId;
    private LocalDate createdDate;
    private String id;


}
