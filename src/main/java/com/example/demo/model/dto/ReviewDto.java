package com.example.demo.model.dto;


import lombok.*;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewDto {

    @NotBlank
    public String title;
    @NotBlank
    public String description;
    public Float stars;
    public String gameId;
    public LocalDate createdDate;
    public String id;


}
