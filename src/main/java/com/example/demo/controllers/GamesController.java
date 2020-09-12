package com.example.demo.controllers;


import com.example.demo.model.Game;
import com.example.demo.model.GamesRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

// https://www.codementor.io/@gtommee97/rest-api-java-spring-boot-and-mongodb-j7nluip8d

@RestController
@RequestMapping("/games")
@AllArgsConstructor
@Slf4j
public class GamesController {

    GamesRepository gamesRepository;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public void createGame(@Valid @RequestBody GamesDTO gamesDTO) throws GameFoundException {
        Game games = gamesRepository.findGameByName(gamesDTO.getName());
        if(games != null) {
            throw new GameFoundException();
        }

        Game game = Game.builder()
                .name(gamesDTO.getName())
                .category(gamesDTO.getCategory())
                .description(gamesDTO.getDescription())
                .platform(gamesDTO.getPlatform()).build();
        gamesRepository.save(game);
    }

    @GetMapping
    public List<Game> getGames() {return gamesRepository.findAll();} //returns list of all games

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void exceptionHandler(GameFoundException gameFoundException) {log.info("brak gry");}







    //private static List<Game> games;

//    static{
//        games = new ArrayList<>();
//        games.add(new Game("1", "Home Escape", "unknown"));
//        games.add(new Game("2", "Subway Surfers", "unknown"));
//        games.add(new Game("3", "Temple Run", "unknown"));
//    }

//    @GetMapping
//    public List<Game> getAllGames() {
//        return games;
//    }

//    @GetMapping("/{gameId}")
//
//    public Game getGame(@PathVariable String gameId) {
//        for(Game game : games){
//            if(game.getId().equals(gameId)){
//                return game;
//            }
//        }
//        return null;
//    }


    public Game getGameByName(String name) {
        return null;
    }
}
