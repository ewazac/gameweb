package com.example.demo.controllers;


import com.example.demo.mapper.UserMapper;
import com.example.demo.model.dao.Favourites;
import com.example.demo.model.dao.News;
import com.example.demo.model.dto.UserDto;
import com.example.demo.services.FavoritesService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/fav")
@CrossOrigin(origins = {"https://gameweb.projektstudencki.pl", "http://localhost:4200"}, allowCredentials = "true")
public class FavoritesController {

    private final FavoritesService favoritesService;
    private final UserMapper userMapper;

    @PostMapping("{newsId}")
    @PreAuthorize("isAuthenticated()")
    @ApiOperation(value = "Method for adding news to favourites. Only for logged in users")
    public Favourites addNewsToFavorites(@PathVariable String newsId) {
        return favoritesService.addToFavourite(newsId);
    }

    @GetMapping()
    @PreAuthorize("isAuthenticated()")
    @ApiOperation(value = "Displays list of favourites news for current user")
    public List<News> getFavouriteNews() {
        return favoritesService.getFavoritesNewsForUser();
    }

    @GetMapping("/news/allFav")
    @PreAuthorize("isAuthenticated()")
    @ApiOperation(value = "Displays list of all favourites for current user")
    public List<Favourites> getAllFavourites() {
        return favoritesService.getAllFavourites();
    }

    @GetMapping("/game")
    @PreAuthorize("isAuthenticated()")
    @ApiOperation(value = "Displays list of favourites games for current user")
    public List<String> getFavouriteGames() {
        return favoritesService.getFavoritesGamesForUser();
    }

    @PostMapping("/game/{gameId}")
    @PreAuthorize("isAuthenticated()")
    @ApiOperation(value = "Method for adding game to favourites")
    public void addGameToFavourites(@PathVariable String gameId) {
        favoritesService.addGameToFavourite(gameId);
    }

    @DeleteMapping("/{favouriteId}")
    @PreAuthorize("isAuthenticated()")
    @ApiOperation(value = "Method for deleting favourite news or game from favourites")
    public void deleteFromFavourites(@PathVariable String favouriteId) {
        favoritesService.deleteFromFavourite(favouriteId);
    }

    @GetMapping("/users")
    @PreAuthorize("isAuthenticated()")
    @ApiOperation(value = "Displays list of users that have particular game in favourites")
    public List<UserDto> getUserFavourites(@RequestParam String gameId) {
        return userMapper.toListDto(favoritesService.getAllUsersFavourites(gameId));
    }

}
