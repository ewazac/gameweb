package com.example.demo.controllers;


import com.example.demo.mapper.UserMapper;
import com.example.demo.model.dao.Favourites;
import com.example.demo.model.dao.News;
import com.example.demo.model.dto.UserDto;
import com.example.demo.services.FavoritesService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/fav")
@CrossOrigin(origins = {"https://gameweb2.herokuapp.com", "http://localhost:4200"}, allowCredentials = "true")
public class FavoritesController {

    private final FavoritesService favoritesService;
    private final UserMapper userMapper;

    @PostMapping("{newsId}")
    public Favourites addNewsToFavorites(@PathVariable String newsId) {
        return favoritesService.addToFavourite(newsId);
    }

    @GetMapping()
    public List<News> getFavouriteNews() {
        return favoritesService.getFavoritesNewsForUser();
    }

    @GetMapping("/news/allFav")
    public List<Favourites> getAllFavourites() {
        return favoritesService.getAllFavourites();
    }

    @GetMapping("/game")
    public List<String> getFavouriteGames() {
        return favoritesService.getFavoritesGamesForUser();
    }

    @PostMapping("/game/{gameId}")
    public void addGameToFavourites(@PathVariable String gameId) {
        favoritesService.addGameToFavourite(gameId);
    }

    @DeleteMapping("/{favouriteId}")
    @PreAuthorize("isAuthenticated()")
    public void deleteFromFavourites(@PathVariable String favouriteId) {
        favoritesService.deleteFromFavourite(favouriteId);
    }

    @GetMapping("/users")
    public List<UserDto> getUserFavourites(@RequestParam String gameId) {
        return userMapper.toListDto(favoritesService.getAllUsersFavourites(gameId));
    }

}
