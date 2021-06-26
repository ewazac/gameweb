package com.example.demo.repository;

import com.example.demo.model.dao.News;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface NewsRepository extends MongoRepository<News, String> {

    News findNewsByTitle(String title);

    List<News> findByIdIn(List<String> ids);

    List<News> findByCreatedDateIsAfter(LocalDate date);

    List<News> findByCreatedDateIsAfterAndSendMailIsTrue(LocalDate minusDays);
}
