package com.example.demo.model.dto;


import lombok.*;
import org.bson.types.Binary;
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
    private Binary image;
    private String userId;
    private String imageUrl;
    private String gameName;


}
