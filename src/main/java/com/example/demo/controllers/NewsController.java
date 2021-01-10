package com.example.demo.controllers;


import com.example.demo.model.dao.News;
import com.example.demo.repository.NewsRepository;
import com.example.demo.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@AllArgsConstructor
@RestController
@RequestMapping(value = "/news")
public class NewsController {

    NewsRepository newsRepository;
    UserRepository userRepository;

    @PostMapping(value = "/addNews")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void addNews(String title, String description, MultipartFile image) throws IOException {
        News news = News.builder()
                .title(title)
                .description(description)
                .image(new Binary(BsonBinarySubType.BINARY, image.getBytes()))
                .build();
        newsRepository.save(news);
    }

    @GetMapping(value = "/newsImage/{title}")
    public Binary displayNewsImage(@PathVariable String title) {
        News news = newsRepository.findNewsByTitle(title);
        return news.getImage();
    }

    @GetMapping
    public List<News> getNews() {
        return newsRepository.findAll();  //display list of all news
    }





}
