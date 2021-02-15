package com.example.demo.controllers;


import com.example.demo.mapper.NewsMapper;
import com.example.demo.model.dao.News;
import com.example.demo.repository.NewsRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.services.NewsService;
import lombok.AllArgsConstructor;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


@AllArgsConstructor
@RestController
@RequestMapping(value = "/news")
public class NewsController {

    NewsRepository newsRepository;
    UserRepository userRepository;

    NewsService newsService;
    NewsMapper newsMapper;

    @PostMapping(value = "/addNews")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void addNews(String title, String description, MultipartFile image, String body) throws IOException {
        News news = News.builder()
                .title(title)
                .description(description)
                .image(new Binary(BsonBinarySubType.BINARY, image.getBytes()))
                .body(body)
                .build();
        newsRepository.save(news);
    }

    @GetMapping(value = "/newsImage/{title}")
    public Binary displayNewsImage(@PathVariable String title) {
        News news = newsRepository.findNewsByTitle(title);
        return news.getImage();
    }

    @GetMapping(value = "/getNewsBody/{newsId}", produces = MediaType.TEXT_HTML_VALUE)
    public String bodyAsHtml(@PathVariable String newsId) {
        News news = newsService.getById(newsId);
        return news.getBody();


    }

    @GetMapping
    public List<News> getNews() {
        return newsRepository.findAll();
    }





}
