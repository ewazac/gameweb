package com.example.demo.controllers;


import com.example.demo.model.dao.News;
import com.example.demo.services.FavoritesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/fav")
@CrossOrigin(origins = {"https://gameweb2.herokuapp.com", "http://localhost:4200"}, allowCredentials = "true")
public class FavoritesController {

    private final FavoritesService favoritesService;

    @PostMapping("{newsId}")
    public void addToFavorites(@PathVariable String newsId) {
        favoritesService.addToFavourite(newsId);
    }

    @GetMapping()
    public List<News> getFavourites() {
        return favoritesService.getFavoritesNewsForUser();
    }

    @GetMapping("/game")
    public List<String> getFavouriteGames() {
        return favoritesService.getFavoritesGamesForUser();
    }

    @PostMapping("/game/{gameId}")
    public void addGameToFavourites(@PathVariable String gameId) {
        favoritesService.addGameToFavourite(gameId);
    }

}
