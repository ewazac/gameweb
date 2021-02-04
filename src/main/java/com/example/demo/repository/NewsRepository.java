package com.example.demo.repository;

import com.example.demo.model.dao.News;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface NewsRepository extends MongoRepository<News, String> {

    News findNewsByTitle(String title);

    List<News> findByIdIn(List<String> ids);



}
