package com.example.demo.repository;

import com.example.demo.model.dao.Forum;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ForumRepository extends MongoRepository<Forum, String> {



}
