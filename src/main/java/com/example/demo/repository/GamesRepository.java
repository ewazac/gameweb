package com.example.demo.model;

import org.springframework.data.mongodb.repository.MongoRepository;
// connector between the model and MongoDB
public interface GamesRepository extends MongoRepository<Game, String > {

    Game findGameByName(String name);
}
