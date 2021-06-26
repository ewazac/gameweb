package com.example.demo.model.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document("favourites")
/**Favourites class is used for adding news and games to user's favourites*/
public class Favourites {

    @Id
    private String id;
    private String userId;
    private String newsId;
    private String gameId;


}
