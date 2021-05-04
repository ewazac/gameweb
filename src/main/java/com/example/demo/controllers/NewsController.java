package com.example.demo.controllers;


import com.example.demo.mapper.NewsMapper;
import com.example.demo.model.dao.News;
import com.example.demo.model.dto.NewsDto;
import com.example.demo.repository.NewsRepository;
import com.example.demo.services.NewsService;
import lombok.AllArgsConstructor;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;


@AllArgsConstructor
@RestController
@RequestMapping(value = "/news")
@CrossOrigin(origins = {"https://gameweb2.herokuapp.com", "http://localhost:4200"}, allowCredentials = "true")
public class NewsController {

    NewsRepository newsRepository;

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

    @PutMapping(value = "/addNewsImage/{id}")
    public News addNewsImage(MultipartFile multipartFile, @PathVariable String id) throws IOException {
        return newsService.addNewsImage(multipartFile, id);
    }

    @GetMapping(value = "/newsImage/{title}")
    public Binary displayNewsImage(@PathVariable String title) {
        News news = newsRepository.findNewsByTitle(title);
        return news.getImage();
    }

    @GetMapping(value = "/getNewsBody/{newsId}", produces = MediaType.TEXT_HTML_VALUE)
    public String bodyAsHtml(@PathVariable String newsId) {
        return newsService.bodyAsHtml(newsId);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteNewsById(@PathVariable String id) {
        newsService.deleteById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public NewsDto updateNewsById(@RequestBody NewsDto newsDto, @PathVariable String id) {
        return newsMapper.toDto(newsService.updateNewsById(newsMapper.toDao(newsDto), id));
    }

    @GetMapping
    public List<News> getNews() {
        return newsRepository.findAll();
    }


}
