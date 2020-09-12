package com.example.demo.controllers;

import lombok.Builder;
import lombok.Getter;
import javax.validation.constraints.NotBlank;

@Builder
@Getter
public class GamesDTO {

    @NotBlank
    public String name;
    public String category;
    public String description;
    public String platform;

}
