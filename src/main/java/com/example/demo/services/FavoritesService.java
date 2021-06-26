package com.example.demo.services;

import com.example.demo.model.dao.Favourites;
import com.example.demo.model.dao.News;
import com.example.demo.model.dao.User;
import com.example.demo.repository.FavouritesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FavoritesService {

    private final FavouritesRepository favouritesRepository;
    private final UserService userService;
    private final NewsService newsService;

    /**Adds news to user's favourites*/
    public Favourites addToFavourite(String newsId) {
        User currentUser = userService.getCurrentUser();
        News news = newsService.getById(newsId);
        return favouritesRepository.save(new Favourites(null, currentUser.getId(), news.getId(), null));
    }

    /**Returns list of news that user has in favourites*/
    public List<News> getFavoritesNewsForUser() {
        User currentUser = userService.getCurrentUser();
        List<Favourites> favourites = favouritesRepository.findByUserId(currentUser.getId());
        List<String> newsIds = favourites.stream()
                .filter(favourite -> favourite.getNewsId() != null)
                .map(Favourites::getNewsId).collect(Collectors.toList());
        return newsService.getNews(newsIds);
    }

    /**Returns list of all user's favourites*/
    public List<Favourites> getAllFavourites() {
        User currentUser = userService.getCurrentUser();
        return favouritesRepository.findByUserId(currentUser.getId());
    }

    /**Returns list of users for particular game*/
    public List<User> getAllUsersFavourites(String gameId) {
        List<String> userIds = favouritesRepository.findByGameId(gameId).stream()
                .map(Favourites::getUserId)
                .collect(Collectors.toList());
        return userService.getUsers(userIds);
    }

    /**Returns list of all user's favourites*/
    public List<Favourites> getFavouritesForUser(String userId) {
        return favouritesRepository.findByUserId(userId);
    }

    /**Adds game to user's favourites*/
    public void addGameToFavourite(String gameId) {
        User currentUser = userService.getCurrentUser();
        favouritesRepository.save(new Favourites(null, currentUser.getId(), null, gameId));
    }

    /**Returns list of games that user has in favourites*/
    public List<String> getFavoritesGamesForUser() {
        User currentUser = userService.getCurrentUser();
        List<Favourites> favourites = favouritesRepository.findByUserId(currentUser.getId());
        List<String> gamesIds = favourites.stream()
                .filter(game -> game.getGameId() != null)
                .map(Favourites::getGameId).collect(Collectors.toList());
        return gamesIds;
    }

    /**Deletes from favourites*/
    public void deleteFromFavourite(String favouriteId) {
        favouritesRepository.deleteById(favouriteId);
    }

}
