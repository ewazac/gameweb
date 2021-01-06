package com.example.demo.model;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface NewsRepository extends MongoRepository<News, String> {

    News findNewsByTitle(String title);



}
