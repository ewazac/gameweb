package com.example.demo.controllers;


import com.example.demo.model.dao.Game;
import com.example.demo.services.GameService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

// https://www.codementor.io/@gtommee97/rest-api-java-spring-boot-and-mongodb-j7nluip8d

@RestController
@RequestMapping("/games")
@AllArgsConstructor
@Slf4j
@CrossOrigin(origins = {"http://localhost:4200/Games","https://gameweb2.herokuapp.com"}, allowCredentials = "true")
public class GamesController {


    private final GameService gameService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<Game> saveGames(@RequestBody List<Game> games) {
        return gameService.save(games);
    }

    @PatchMapping("{gameId}")
    @PreAuthorize("isAuthenticated()")
    public void vote(@PathVariable String gameId, @RequestParam String groupId) throws UserFoundException {
        gameService.vote(gameId, groupId);
    }

    @GetMapping({"{groupId}"})
    @PreAuthorize("isAuthenticated()")
    public List<Game> findByGroupId(@PathVariable String groupId) {
        return gameService.findByGroupId(groupId);
    }

    @DeleteMapping("{groupId}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteByGroupId(@PathVariable String groupId) {
        gameService.deleteGroupById(groupId);
    }

    @PatchMapping("/recommended")
    @PreAuthorize("isAuthenticated()")
    public Game findRecommendedGame(@RequestParam String groupId) {
        return gameService.findRecommendedGame(groupId);
    }

    @GetMapping("/recommended")
    @PreAuthorize("isAuthenticated()")
    public List<Game> findRecommendedGamesList() {
        return gameService.findRecommendedGamesList();
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public Map<String, List<Game>> findLists() {
        return gameService.findLists();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void exceptionHandler(UserFoundException userFoundException) {
        log.info("User has voted for that game!");
    }


}
