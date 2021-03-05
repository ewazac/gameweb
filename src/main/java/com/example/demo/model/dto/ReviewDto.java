package com.example.demo.model.dto;


import com.example.demo.model.dao.User;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;

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
    private String nick;




}
