package com.example.demo.model.dto;

import lombok.Builder;
import lombok.Getter;
import javax.validation.constraints.NotBlank;

@Builder
@Getter
public class GamesDto {

    @NotBlank
    public String name;
    public String category;
    public String description;
    public String platform;

}
