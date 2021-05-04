package com.example.demo.services;


import com.example.demo.exeption.EntityNotFoundException;
import com.example.demo.model.dao.News;
import com.example.demo.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsService {

    private final NewsRepository newsRepository;
    private final FileService fileService;

    public List<News> getAll() {
        return newsRepository.findAll();
    }

    public News save(News news) {
        return newsRepository.save(news);
    }

    public News getById(String id) {
        return newsRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id));
    }

    public News updateNewsById(News news, String id) {
        News newsDb = getById(id);
        newsDb.setDescription(news.getDescription());
        newsDb.setImage(news.getImage());
        newsDb.setTitle(news.getTitle());
        newsDb.setBody(news.getBody());
        return newsRepository.save(newsDb);
    }

    public News addNewsImage(MultipartFile multipartFile, String newsId) throws IOException {
        News news = getById(newsId);
        news.setImageUrl(news.getId() + ".png");
        fileService.uploadFileToNews(multipartFile.getBytes(), news.getId() + ".png");
        return newsRepository.save(news);
    }

    public String bodyAsHtml(String newsId) {
        News news = getById(newsId);
        return news.getBody();
    }

    public void deleteById(String id) {
        newsRepository.deleteById(id);
    }

    public List<News> getNews(List<String> ids) {
        return newsRepository.findByIdIn(ids);
    }





}
