package com.example.demo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.Binary;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewsDto {

    private String id;
    private String title;
    private String description;
    private Binary image;
    private Date createdDate;
    private String body;
    private boolean sendMail;


}
