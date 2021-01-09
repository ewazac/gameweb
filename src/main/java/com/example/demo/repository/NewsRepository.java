package com.example.demo.repository;

import com.example.demo.model.dao.News;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NewsRepository extends MongoRepository<News, String> {

    News findNewsByTitle(String title);



}
