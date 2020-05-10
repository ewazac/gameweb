package com.example.demo.controllers;


import com.example.demo.model.Game;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/games")
public class GamesController {

    private static List<Game> games;

    static{
        games = new ArrayList<>();
        games.add(new Game("1", "Home Escape", "unknown"));
        games.add(new Game("2", "Subway Surfers", "unknown"));
        games.add(new Game("3", "Temple Run", "unknown"));
    }

    @GetMapping
    public List<Game> getAllGames() {
        return games;
    }

    @GetMapping("/{gameId}")

    public Game getGame(@PathVariable String gameId) {
        for(Game game : games){
            if(game.getId().equals(gameId)){
                return game;
            }
        }
        return null;
    }


    public Game getGameByName(String name) {
        return null;
    }
}
