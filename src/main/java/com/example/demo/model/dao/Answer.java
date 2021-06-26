package com.example.demo.model.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** Answer class is used for single answer in forum */
public class Answer {


    @Id
    public String id;
    private String message;
    private LocalDateTime createdDate;
    private String username;
    private Binary avatar;
    private String userId;

}
