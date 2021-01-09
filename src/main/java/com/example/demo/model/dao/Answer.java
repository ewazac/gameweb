package com.example.demo.model.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Answer {

    private String message;
    private LocalDateTime createdDate;
    private String username;

}
