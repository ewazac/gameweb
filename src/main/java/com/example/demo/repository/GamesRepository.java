package com.example.demo.repository;

import com.example.demo.model.dao.Game;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

// connector between the model and MongoDB
public interface GamesRepository extends MongoRepository<Game, String > {

    Game findGameByName(String name);

    List<Game> findByGroupId(String groupId);
}
