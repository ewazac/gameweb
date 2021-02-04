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

    public void addToFavourite(String newsId) {
        User currentUser = userService.getCurrentUser();
        News news = newsService.getById(newsId);
        favouritesRepository.save(new Favourites(null, currentUser.getId(), news.getId()));

    }

    public List<News> getFavoritesNewsForUser() {
        User currentUser = userService.getCurrentUser();
        List<Favourites> favourites = favouritesRepository.findByUserId(currentUser.getId());
        List<String> newsIds = favourites.stream().map(Favourites::getNewsId).collect(Collectors.toList());
        return newsService.getNews(newsIds);
    }

}
