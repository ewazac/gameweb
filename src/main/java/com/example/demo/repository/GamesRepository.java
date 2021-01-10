package com.example.demo.repository;

import com.example.demo.model.dao.Game;
import org.springframework.data.mongodb.repository.MongoRepository;
// connector between the model and MongoDB
public interface GamesRepository extends MongoRepository<Game, String > {

    Game findGameByName(String name);
}
