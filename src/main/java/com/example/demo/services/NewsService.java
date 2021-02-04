package com.example.demo.services;


import com.example.demo.exeption.EntityNotFoundException;
import com.example.demo.model.dao.News;
import com.example.demo.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsService {

    private final NewsRepository newsRepository;

    public List<News> getAll() {
        return newsRepository.findAll();
    }

    public News save(News news) {
        return newsRepository.save(news);
    }

    public News getById(String id) {
        return newsRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id));
    }

    public News update(News news, String id) {
        News newsDb = getById(id);
        newsDb.setDescription(news.getDescription());
        newsDb.setImage(news.getImage());
        newsDb.setTitle(news.getTitle());
        return newsRepository.save(newsDb);
    }

    public void deleteById(String id) {
        newsRepository.deleteById(id);
    }

    public List<News> getNews(List<String> ids) {
        return newsRepository.findByIdIn(ids);
    }





}
