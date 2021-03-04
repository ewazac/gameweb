package com.example.demo.controllers;


import com.example.demo.model.dao.Game;
import com.example.demo.repository.GamesRepository;
import com.example.demo.model.dto.GamesDto;
import com.example.demo.services.GameService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.sort;

// https://www.codementor.io/@gtommee97/rest-api-java-spring-boot-and-mongodb-j7nluip8d

@RestController
@RequestMapping("/games")
@AllArgsConstructor
@Slf4j
@CrossOrigin(origins = {"http://localhost:4200/Games","https://gameweb2.herokuapp.com"}, allowCredentials = "true")
public class GamesController {


    private final GameService gameService;

    @PostMapping
    public List<Game> saveGames(@RequestBody List<Game> games) {
        return gameService.save(games);
    }

    @PatchMapping("{gameId}")
    public void vote(@PathVariable String gameId, @RequestParam String groupId) {
        gameService.vote(gameId, groupId);
    }

    @GetMapping({"{groupId}"})
    public List<Game> findByGroupId(@PathVariable String groupId) {
        return gameService.findByGroupId(groupId);
    }

    @DeleteMapping("{groupId}")
    public void deleteByGroupId(@PathVariable String groupId) {
        gameService.deleteGroupById(groupId);
    }

    @PatchMapping("/recommended")
    public Game findRecommendedGame(@RequestParam String groupId) {
        return gameService.findRecommendedGame(groupId);
    }

    @GetMapping("/recommended")
    public List<Game> findRecommendedGamesList() {
        return gameService.findRecommendedGamesList();
    }

    @GetMapping
    public Map<String, List<Game>> findLists() {
        return gameService.findLists();
    }

//    GamesRepository gamesRepository;
//    @Autowired
//    MongoTemplate mongoTemplate;
//
//    @PostMapping
//    @ResponseStatus(value = HttpStatus.CREATED)
//    public void createGame(@Valid @RequestBody GamesDto gamesDTO) throws GameFoundException {
//        Game games = gamesRepository.findGameByName(gamesDTO.getName());
//        if(games != null) {
//            throw new GameFoundException();
//        }
//
//        Game game = Game.builder()
//                .name(gamesDTO.getName())
//                .category(gamesDTO.getCategory())
//                .description(gamesDTO.getDescription())
//                .platform(gamesDTO.getPlatform()).build();
//        gamesRepository.save(game);
//    }
//
//    @PostMapping(value = "uploadGameImage")
//    @ResponseStatus(value = HttpStatus.CREATED)
//    public void uploadImage(@RequestParam("gameimage") MultipartFile multipartFile, String name) throws IOException {
//        Game game = gamesRepository.findGameByName(name);
//        if(game != null) {
//            game.setImage(new Binary(BsonBinarySubType.BINARY, multipartFile.getBytes()));
//            gamesRepository.save(game);
//        }
//    }
//
//    @GetMapping(value = "getGameImage")
//    public Binary getGameImage(String name) {
//        Game game = gamesRepository.findGameByName(name);
//        return game.getGameImage();
//    }
//
//    @GetMapping(value = "/{name}")
//    public Game getGame(@PathVariable String name) {
//        return gamesRepository.findGameByName(name);
//    }
//
//
//
//    @GetMapping
//    public List<Game> getGames() {
//        return gamesRepository.findAll();
//    } //returns list of all games
//
//
//
//
//    @ExceptionHandler
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public void exceptionHandler(GameFoundException gameFoundException) {log.info("brak gry");}
//
//
//    public Game getGameByName(String name) {
//        return null;
//    }
}
