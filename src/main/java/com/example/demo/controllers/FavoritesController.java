package com.example.demo.controllers;


import com.example.demo.model.dao.News;
import com.example.demo.services.FavoritesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/fav")
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

}
