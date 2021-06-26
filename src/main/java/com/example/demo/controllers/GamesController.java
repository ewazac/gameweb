package com.example.demo.controllers;


import com.example.demo.model.dao.Game;
import com.example.demo.services.GameService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/games")
@AllArgsConstructor
@Slf4j
@Api(value = "competition", description = "Games is representation of competitions where users can take part in by voting for the best games")
@CrossOrigin(origins = {"http://localhost:4200/Games","https://gameweb.projektstudencki.pl"}, allowCredentials = "true")
public class GamesController {

    private final GameService gameService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Add games to create new competition")
    public List<Game> saveGames(@RequestBody List<Game> games) {
        return gameService.save(games);
    }

    @PatchMapping("{gameId}")
    @PreAuthorize("isAuthenticated()")
    @ApiOperation(value = "Method for voting for the best game. Only logged in users can vote. Every vote gives one point for score the is needed to find recommended game")
    public void vote(@PathVariable String gameId, @RequestParam String groupId) throws UserFoundException {
        gameService.vote(gameId, groupId);
    }

    @GetMapping({"{groupId}"})
    @PreAuthorize("isAuthenticated()")
    @ApiOperation(value = "Displays all games in particular competition. Only for logged in users")
    public List<Game> findByGroupId(@PathVariable String groupId) {
        return gameService.findByGroupId(groupId);
    }

    @DeleteMapping("{groupId}")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Method for deleting competition")
    public void deleteByGroupId(@PathVariable String groupId) {
        gameService.deleteGroupById(groupId);
    }

    @PatchMapping("/recommended")
    @PreAuthorize("isAuthenticated()")
    @ApiOperation(value = "Method for finding recommended game in competition with the highest score")
    public Game findRecommendedGame(@RequestParam String groupId) {
        return gameService.findRecommendedGame(groupId);
    }

    @GetMapping("/recommended")
    @PreAuthorize("isAuthenticated()")
    @ApiOperation(value = "Displays all recommended games")
    public List<Game> findRecommendedGamesList() {
        return gameService.findRecommendedGamesList();
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    @ApiOperation(value = "Displays all competitions")
    public Map<String, List<Game>> findLists() {
        return gameService.findLists();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void exceptionHandler(UserFoundException userFoundException) {
        log.info("User has voted for that competition!");
    }


}
